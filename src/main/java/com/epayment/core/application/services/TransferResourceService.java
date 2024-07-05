package com.epayment.core.application.services;

import java.math.BigDecimal;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.*;

import com.epayment.core.domain.Account;
import com.epayment.core.domain.events.BalanceChanged;
import com.epayment.core.domain.events.TransactionFailed;
import com.epayment.core.domain.EventDispatcher;
import com.epayment.core.domain.Transaction;
import com.epayment.core.domain.exceptions.OperationalException;
import com.epayment.core.application.repositories.AccountRepository;
import com.epayment.core.application.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferResourceService {
  private TransactionRepository transactionRepository;
  private AccountRepository accountRepository;
  private EventDispatcher<BalanceChanged> balanceChangedDispatcher;
  private EventDispatcher<TransactionFailed> transactionFailedDispatcher;

  public TransferResourceService(
    TransactionRepository transactionRepository,
    AccountRepository accountRepository,
    EventDispatcher<BalanceChanged> balanceChangedDispatcher,
    EventDispatcher<TransactionFailed> transactionFailedDispatcher
  ) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.balanceChangedDispatcher = balanceChangedDispatcher;
    this.transactionFailedDispatcher = transactionFailedDispatcher;
  }

  @Transactional
  public Transaction execute(TransferResourceService.Request request) {
    var sender = this.getAccountBy(request.senderEmail);
    var receiver = this.getAccountBy(request.receiverEmail);
    var context = new Context(sender, receiver, request.amount);

    try { return transferResource(context); }
    catch (Exception exception) {
      handleRollback(context);
      throw exception;
    }
  }

  private Transaction transferResource(Context context) {
    if (context.sender == null) throw new OperationalException("sender does not exist");
    if (context.receiver == null) throw new OperationalException("receiver does not exist");

    var transaction = new Transaction();

    transaction.setEndpoints(context.sender, context.receiver);
    transaction.setAmount(context.amount);
    transaction.execute();

    this.accountRepository.save(context.sender);
    this.accountRepository.save(context.receiver);
    this.transactionRepository.save(transaction);
    
    transaction
      .getEvents()
      .forEach(this.balanceChangedDispatcher::dispatch);

    return transaction;
  }

  private void handleRollback(Context context) {
    this.transactionFailedDispatcher.dispatch(
      new TransactionFailed(
        context.amount,
        TransactionFailed.Endpoint.of(context.sender),
        TransactionFailed.Endpoint.of(context.receiver)
      )
    );
  }

  private Account getAccountBy(String email) {
    var accountQuery = this.accountRepository.findByEmail(email);
    return accountQuery.isPresent() ? accountQuery.get() : null;
  }

  public static record Request(
    @NotNull @Email String senderEmail,
    @NotNull @Email String receiverEmail,
    @NotNull BigDecimal amount
  ) {}

  private static record Context(Account sender, Account receiver, BigDecimal amount) {}
}

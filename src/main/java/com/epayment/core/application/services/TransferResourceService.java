package com.epayment.core.application.services;

import java.math.BigDecimal;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.domain.EventDispatcher;
import com.epayment.core.domain.Transaction;
import com.epayment.core.domain.exceptions.OperationalException;
import com.epayment.core.application.repositories.AccountRepository;
import com.epayment.core.application.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransferResourceService {
  private TransactionRepository transactionRepository;
  private AccountRepository accountRepository;
  private EventDispatcher<BalanceChanged> eventDispatcher;

  public TransferResourceService(
    TransactionRepository transactionRepository,
    AccountRepository accountRepository,
    EventDispatcher<BalanceChanged> eventDispatcher
  ) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.eventDispatcher = eventDispatcher;
  }

  @Transactional
  public Transaction execute(TransferResourceService.Request request) {
    var senderQuery = this.accountRepository.findByEmail(request.senderEmail);
    var receiverQuery = this.accountRepository.findByEmail(request.receiverEmail);

    if (senderQuery.isEmpty()) throw new OperationalException("sender does not exist");
    if (receiverQuery.isEmpty()) throw new OperationalException("receiver does not exist");
    
    var sender = senderQuery.get();
    var receiver = receiverQuery.get();

    var transaction = new Transaction();

    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(request.amount);
    transaction.execute();

    this.accountRepository.save(sender);
    this.accountRepository.save(receiver);
    this.transactionRepository.save(transaction);
    
    transaction
      .getEvents()
      .forEach(this.eventDispatcher::dispatch);

    return transaction;
  }

  public static record Request(
    String senderEmail,
    String receiverEmail,
    BigDecimal amount
  ) {}
}

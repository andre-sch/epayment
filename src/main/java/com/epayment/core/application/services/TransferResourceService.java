package com.epayment.core.application.services;

import java.math.BigDecimal;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.domain.EventDispatcher;
import com.epayment.core.domain.Transaction;
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
    var senderQuery = this.accountRepository.findById(request.senderId); // todo: replace by email
    var receiverQuery = this.accountRepository.findById(request.receiverId);

    if (senderQuery.isEmpty()) throw new RuntimeException("sender does not exist");
    if (receiverQuery.isEmpty()) throw new RuntimeException("receiver does not exist");
    
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
    int senderId,
    int receiverId,
    BigDecimal amount
  ) {}
}

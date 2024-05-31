package com.epayment.core.services.transferResource;

import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.domain.EventDispatcher;
import com.epayment.core.domain.Transaction;
import com.epayment.core.repositories.WalletRepository;
import com.epayment.core.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransferResourceService {
  private TransactionRepository transactionRepository;
  private WalletRepository walletRepository;
  private EventDispatcher<BalanceChanged> eventDispatcher;

  public TransferResourceService(
    TransactionRepository transactionRepository,
    WalletRepository walletRepository,
    EventDispatcher<BalanceChanged> eventDispatcher
  ) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
    this.eventDispatcher = eventDispatcher;
  }

  @Transactional
  public Transaction execute(ResourceTransferenceRequest request) {
    var senderSearch = this.walletRepository.findById(request.senderId);
    var receiverSearch = this.walletRepository.findById(request.receiverId);

    if (senderSearch.isEmpty()) throw new RuntimeException("sender does not exist");
    if (receiverSearch.isEmpty()) throw new RuntimeException("receiver does not exist");
    
    var sender = senderSearch.get();
    var receiver = receiverSearch.get();

    var transaction = new Transaction();

    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(request.amount);
    transaction.execute();

    this.walletRepository.save(sender);
    this.walletRepository.save(receiver);
    this.transactionRepository.save(transaction);
    
    transaction
      .getEvents()
      .forEach(this.eventDispatcher::dispatch);

    return transaction;
  }
}

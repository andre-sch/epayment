package com.epayment.core.services.transferResource;

import com.epayment.core.entities.Transaction;
import com.epayment.core.repositories.WalletRepository;
import com.epayment.core.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransferResourceService {
  private TransactionRepository transactionRepository;
  private WalletRepository walletRepository;

  public TransferResourceService(
    TransactionRepository transactionRepository,
    WalletRepository walletRepository
  ) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
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

    return transaction;
  }
}

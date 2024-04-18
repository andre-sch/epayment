package com.moneytransactions.core.transaction.transference;

import jakarta.transaction.Transactional;
import com.moneytransactions.core.transaction.Transaction;
import com.moneytransactions.core.transaction.TransactionRepository;
import com.moneytransactions.core.wallet.WalletRepository;
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

    if (senderSearch.isEmpty()) throw new TransactionEndpointAbsentException("sender does not exist");
    if (receiverSearch.isEmpty()) throw new TransactionEndpointAbsentException("receiver does not exist");
    
    var sender = senderSearch.get();
    var receiver = receiverSearch.get();

    sender.debit(request.amount);
    receiver.credit(request.amount);

    this.walletRepository.save(sender);
    this.walletRepository.save(receiver);

    var transaction = new Transaction();

    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(request.amount);

    this.transactionRepository.save(transaction);

    return transaction;
  }
}

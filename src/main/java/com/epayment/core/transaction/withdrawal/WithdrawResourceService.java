package com.epayment.core.transaction.withdrawal;

import jakarta.transaction.Transactional;
import com.epayment.core.transaction.*;
import com.epayment.core.wallet.*;

import org.springframework.stereotype.Service;

@Service
public class WithdrawResourceService {
  private TransactionRepository transactionRepository;
  private WalletRepository walletRepository;
  private CashMachine cashMachine;

  public WithdrawResourceService(
    TransactionRepository transactionRepository,
    WalletRepository walletRepository,
    CashMachine cashMachine
  ) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
    this.cashMachine = cashMachine;
  }

  @Transactional
  public Transaction execute(ResourceWithdrawalRequest request) {
    var withdrawerSearch = this.walletRepository.findById(request.withdrawerId);
    
    if (withdrawerSearch.isEmpty()) {
      throw new TransactionEndpointAbsentException("withdrawer does not exist");
    }

    var withdrawer = withdrawerSearch.get();

    withdrawer.debit(request.amount);
    this.walletRepository.save(withdrawer);
    
    this.cashMachine.withdraw(request.amount);

    var transaction = new Transaction();

    transaction.setEndpoints(withdrawer, null);
    transaction.setAmount(request.amount);

    this.transactionRepository.save(transaction);
    
    return transaction;
  }
}

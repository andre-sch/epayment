package com.epayment.core.transaction.deposit;

import jakarta.transaction.Transactional;
import com.epayment.core.transaction.*;
import com.epayment.core.wallet.*;

import org.springframework.stereotype.Service;

@Service
public class DepositResourceService {
  private TransactionRepository transactionRepository;
  private WalletRepository walletRepository;
  private CashMachine cashMachine;

  public DepositResourceService(
    TransactionRepository transactionRepository,
    WalletRepository walletRepository,
    CashMachine cashMachine
  ) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
    this.cashMachine = cashMachine;
  }
  
  @Transactional
  public Transaction execute(int depositorId) {
    var depositorSearch = this.walletRepository.findById(depositorId);

    if (depositorSearch.isEmpty()) {
      throw new TransactionEndpointAbsentException("depositor does not exist");
    }

    var depositor = depositorSearch.get();

    var amount = cashMachine.readDepositedAmount();
    depositor.credit(amount);

    this.walletRepository.save(depositor);
    
    var transaction = new Transaction();

    transaction.setEndpoints(null, depositor);
    transaction.setAmount(amount);

    this.transactionRepository.save(transaction);
    
    return transaction;
  }
}

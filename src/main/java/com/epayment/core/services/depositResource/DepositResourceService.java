package com.epayment.core.services.depositResource;

import com.epayment.core.interfaces.CashMachine;
import com.epayment.core.entities.Transaction;
import com.epayment.core.repositories.WalletRepository;
import com.epayment.core.repositories.TransactionRepository;
import com.epayment.core.exceptions.TransactionEndpointAbsentException;
import jakarta.transaction.Transactional;
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

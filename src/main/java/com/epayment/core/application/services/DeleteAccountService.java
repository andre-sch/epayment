package com.epayment.core.application.services;

import com.epayment.core.application.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccountService {
  private AccountRepository accountRepository;
  
  public DeleteAccountService(
    AccountRepository accountRepository
  ) {
    this.accountRepository = accountRepository;
  }

  public void execute(String email) {
    var accountQuery = this.accountRepository.findByEmail(email);

    if (accountQuery.isEmpty()) {
      throw new RuntimeException("account does not exist");
    }

    var account = accountQuery.get();
    
    this.accountRepository.delete(account);
  }
}

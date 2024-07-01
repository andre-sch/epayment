package com.epayment.core.application.services;

import com.epayment.core.domain.AccountEvent;
import com.epayment.core.domain.EventDispatcher;
import com.epayment.core.domain.exceptions.OperationalException;
import com.epayment.core.application.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccountService {
  private AccountRepository accountRepository;
  private EventDispatcher<AccountEvent> eventDispatcher;
  
  public DeleteAccountService(
    AccountRepository accountRepository,
    EventDispatcher<AccountEvent> eventDispatcher
  ) {
    this.accountRepository = accountRepository;
    this.eventDispatcher = eventDispatcher;
  }

  public void execute(String email) {
    var accountQuery = this.accountRepository.findByEmail(email);

    if (accountQuery.isEmpty()) {
      throw new OperationalException("account does not exist");
    }

    var account = accountQuery.get();
    
    account.delete();
    this.accountRepository.save(account);

    account
      .getEvents()
      .forEach(eventDispatcher::dispatch);
  }
}

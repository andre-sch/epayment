package com.epayment.core.utils;

import java.math.BigDecimal;
import com.epayment.core.domain.Account;
import com.epayment.core.application.services.CreateAccountService;

public class DummyAccountFactory extends DummyFactory<Account> {
  private final BigDecimal amount = BigDecimal.valueOf(1L, 2);

  public Account build() {
    var account = new Account();

    account.setFullName(dummy());
    account.setEmail(dummy());
    account.setPassword(dummy());
    
    account.credit(amount);

    return account;
  }

  public CreateAccountService.Request buildRequest() {
    var account = build();
    return new CreateAccountService.Request(
      account.getFullName(),
      account.getEmail(),
      account.getPassword()
    );
  }
}

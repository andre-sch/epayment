package com.epayment.core.domain;

import java.math.BigDecimal;

public record AccountCreated(
  BigDecimal balance,
  String email,
  String fullName
) implements AccountEvent {
  public static AccountCreated of(Account account) {
    return new AccountCreated(
      account.getBalance(),
      account.getEmail(),
      account.getFullName()
    );
  }

  public String key() {
    return email;
  }
}

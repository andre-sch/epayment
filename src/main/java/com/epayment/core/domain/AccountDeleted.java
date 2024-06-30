package com.epayment.core.domain;

import java.math.BigDecimal;

public record AccountDeleted(
  int id,
  BigDecimal balance
) implements AccountEvent {
  public static AccountDeleted of(Account account) {
    return new AccountDeleted(
      account.getId(),
      account.getBalance()
    );
  }

  public String key() {
    return Integer.toString(id);
  }
}

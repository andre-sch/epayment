package com.epayment.core.domain;

import java.math.BigDecimal;

public record AccountDeleted(
  int id,
  String email,
  String fullName,
  BigDecimal oldBalance
) implements AccountEvent {
  public static AccountDeleted of(Account account) {
    return new AccountDeleted(
      account.getId(),
      account.getEmail(),
      account.getFullName(),
      account.getBalance()
    );
  }
}

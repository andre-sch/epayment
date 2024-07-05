package com.epayment.core.domain.events;

import java.math.BigDecimal;
import com.epayment.core.domain.Account;

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

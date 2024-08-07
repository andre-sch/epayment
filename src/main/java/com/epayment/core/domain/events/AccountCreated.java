package com.epayment.core.domain.events;

import java.math.BigDecimal;
import com.epayment.core.domain.Account;

public record AccountCreated(
  int id,
  String email,
  String fullName,
  BigDecimal balance
) implements AccountEvent {
  public static AccountCreated of(Account account) {
    return new AccountCreated(
      account.getId(),
      account.getEmail(),
      account.getFullName(),
      account.getBalance()
    );
  }
}

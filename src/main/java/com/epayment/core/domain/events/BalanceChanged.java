package com.epayment.core.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import com.epayment.core.domain.Account;

public record BalanceChanged(
  BigDecimal delta,
  Endpoint client,
  Endpoint partner,
  Instant timestamp
) {
  public static record Endpoint(int id, String email, String fullName) {
    public static Endpoint of(Account account) {
      return new Endpoint(
        account.getId(),
        account.getEmail(),
        account.getFullName()
      );
    }
  }
}

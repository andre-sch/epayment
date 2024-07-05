package com.epayment.core.domain.events;

import java.math.BigDecimal;
import com.epayment.core.domain.Account;

public record TransactionFailed(
  BigDecimal amount,
  Endpoint sender,
  Endpoint receiver
) {
  public static record Endpoint(int id, String email, String fullName) {
    public static Endpoint of(Account account) {
      return new Endpoint(
        account != null ? account.getId() : 0,
        account != null ? account.getEmail() : "",
        account != null ? account.getFullName() : ""
      );
    }
  }
}

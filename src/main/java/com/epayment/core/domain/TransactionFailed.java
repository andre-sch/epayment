package com.epayment.core.domain;

import java.math.BigDecimal;

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

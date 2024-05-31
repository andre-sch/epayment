package com.epayment.core.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record BalanceChanged(
  BigDecimal delta,
  Endpoint client,
  Endpoint partner,
  Instant timestamp
) {
  public static record Endpoint(String email, String fullName) {}
}

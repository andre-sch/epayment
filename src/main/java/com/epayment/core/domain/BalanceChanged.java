package com.epayment.core.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record BalanceChanged(Endpoint client, BigDecimal delta, Metadata metadata) {
  public static record Metadata(Endpoint partner, Instant timestamp) {}
  public static record Endpoint(String email, String fullName) {}
}

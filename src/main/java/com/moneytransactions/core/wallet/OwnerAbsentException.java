package com.moneytransactions.core.wallet;

public class OwnerAbsentException extends RuntimeException {
  public OwnerAbsentException(String message) {
    super(message);
  }
}

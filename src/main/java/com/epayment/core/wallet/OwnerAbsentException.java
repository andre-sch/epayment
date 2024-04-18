package com.epayment.core.wallet;

public class OwnerAbsentException extends RuntimeException {
  public OwnerAbsentException(String message) {
    super(message);
  }
}

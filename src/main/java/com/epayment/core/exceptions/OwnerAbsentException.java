package com.epayment.core.exceptions;

public class OwnerAbsentException extends RuntimeException {
  public OwnerAbsentException(String message) {
    super(message);
  }
}

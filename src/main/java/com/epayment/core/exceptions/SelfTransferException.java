package com.epayment.core.exceptions;

public class SelfTransferException extends RuntimeException {
  public SelfTransferException(String message) {
    super(message);
  }
}

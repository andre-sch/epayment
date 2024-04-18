package com.epayment.core.transaction;

public class SelfTransferException extends RuntimeException {
  public SelfTransferException(String message) {
    super(message);
  }
}

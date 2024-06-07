package com.epayment.core.domain.exceptions;

public class SelfTransferException extends RuntimeException {
  public SelfTransferException(String message) {
    super(message);
  }
}

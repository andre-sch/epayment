package com.epayment.core.domain.exceptions;

public class SelfTransferException extends OperationalException {
  public SelfTransferException(String message) {
    super(message);
  }
}

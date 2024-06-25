package com.epayment.core.domain.exceptions;

public class InsufficientFundsException extends OperationalException {
  public InsufficientFundsException(String message) {
    super(message);
  }
}

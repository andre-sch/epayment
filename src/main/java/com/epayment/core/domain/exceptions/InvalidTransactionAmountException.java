package com.epayment.core.domain.exceptions;

public class InvalidTransactionAmountException extends OperationalException {
  public InvalidTransactionAmountException(String message) {
    super(message);
  }
}

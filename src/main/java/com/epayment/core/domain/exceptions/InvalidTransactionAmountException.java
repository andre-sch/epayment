package com.epayment.core.domain.exceptions;

public class InvalidTransactionAmountException extends RuntimeException {
  public InvalidTransactionAmountException(String message) {
    super(message);
  }
}

package com.epayment.core.exceptions;

public class InvalidTransactionAmountException extends RuntimeException {
  public InvalidTransactionAmountException(String message) {
    super(message);
  }
}

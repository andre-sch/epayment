package com.epayment.core.transaction;

public class InvalidTransactionAmountException extends RuntimeException {
  public InvalidTransactionAmountException(String message) {
    super(message);
  }
}

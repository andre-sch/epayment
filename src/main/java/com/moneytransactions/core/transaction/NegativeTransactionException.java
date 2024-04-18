package com.moneytransactions.core.transaction;

public class NegativeTransactionException extends RuntimeException {
  public NegativeTransactionException(String message) {
    super(message);
  }
}

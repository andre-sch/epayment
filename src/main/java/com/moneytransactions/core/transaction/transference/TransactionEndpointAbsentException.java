package com.moneytransactions.core.transaction.transference;

public class TransactionEndpointAbsentException extends RuntimeException {
  public TransactionEndpointAbsentException(String message) {
    super(message);
  }
}

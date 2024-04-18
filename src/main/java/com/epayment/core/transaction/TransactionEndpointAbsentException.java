package com.epayment.core.transaction;

public class TransactionEndpointAbsentException extends RuntimeException {
  public TransactionEndpointAbsentException(String message) {
    super(message);
  }
}

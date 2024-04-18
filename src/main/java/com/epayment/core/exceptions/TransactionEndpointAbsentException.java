package com.epayment.core.exceptions;

public class TransactionEndpointAbsentException extends RuntimeException {
  public TransactionEndpointAbsentException(String message) {
    super(message);
  }
}

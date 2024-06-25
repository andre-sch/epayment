package com.epayment.core.domain.exceptions;

public class OperationalException extends RuntimeException {
  private Object body;
  
  public OperationalException(String message) { super(message); }
  public OperationalException(String message, Object body) {
    this(message);
    this.body = body;
  }

  public Object getBody() {
    return this.body;
  }
}
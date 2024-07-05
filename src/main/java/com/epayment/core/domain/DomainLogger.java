package com.epayment.core.domain;

public interface DomainLogger {
  public void info(String message);
  public void info(String format, Object... arguments);
}

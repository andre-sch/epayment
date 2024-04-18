package com.epayment.core.transaction;

import java.math.BigDecimal;

public interface CashMachine {
  public void withdraw(BigDecimal amount);
}

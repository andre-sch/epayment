package com.epayment.core.interfaces;

import java.math.BigDecimal;

public interface CashMachine {
  public void withdraw(BigDecimal amount);
  public BigDecimal readDepositedAmount();
}

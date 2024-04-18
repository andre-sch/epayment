package com.epayment.core;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component // stub
public class CashMachine {
  public void withdraw(BigDecimal amount) {}
  public BigDecimal readDepositedAmount() {
    return BigDecimal.TEN;
  }
}

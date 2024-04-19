package com.epayment.core.providers;

import java.math.BigDecimal;
import com.epayment.core.interfaces.CashMachine;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class CashMachineProvider {
  public @Bean CashMachine provideCashMachineStub() {
    return new CashMachineStub();
  }
}

class CashMachineStub implements CashMachine {
  public void withdraw(BigDecimal amount) {}
  public BigDecimal readDepositedAmount() {
    return BigDecimal.TEN;
  }
}
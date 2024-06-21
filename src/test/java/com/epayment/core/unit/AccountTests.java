package com.epayment.core.unit;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.domain.exceptions.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTests {
  @Test
  public void accountCredit() {
    var account = new Account();

    account.credit(BigDecimal.ONE);
    
    assertThat(account.getBalance()).isEqualTo(BigDecimal.ONE);
  }
  
  @Test
  public void accountDebitSucceeds() {
    var account = new Account();
    account.credit(new BigDecimal(3));
    
    account.debit(new BigDecimal(2));

    assertThat(account.getBalance()).isEqualTo(BigDecimal.ONE);
  }

  public void accountDebitFailsWithInsufficientFunds() {
    var account = new Account();

    assertThrows(InsufficientFundsException.class, () -> {
      account.debit(BigDecimal.ONE);
    });
  }
}

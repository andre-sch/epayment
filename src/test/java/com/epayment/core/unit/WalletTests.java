package com.epayment.core.unit;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.domain.exceptions.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTests {
  @Test
  public void walletCredit() {
    var wallet = new Wallet();

    wallet.credit(BigDecimal.ONE);
    
    assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ONE);
  }
  
  @Test
  public void walletDebitSucceeds() {
    var wallet = new Wallet();
    wallet.credit(new BigDecimal(3));
    
    wallet.debit(new BigDecimal(2));

    assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ONE);
  }

  public void walletDebitFailsWithInsufficientFunds() {
    var wallet = new Wallet();

    assertThrows(InsufficientFundsException.class, () -> {
      wallet.debit(BigDecimal.ONE);
    });
  }
}

package com.epayment.core;

import java.math.BigDecimal;
import com.epayment.core.user.User;
import com.epayment.core.wallet.Wallet;
import com.epayment.core.wallet.InsufficientFundsException;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTests {
  @Test
  public void walletCreation() {
    var wallet = new Wallet();
    wallet.setOwner(new User());

    assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ZERO);
  }

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

  public void walletDebitFailsWhenBalanceIsNotEnough() {
    var wallet = new Wallet();

    assertThrows(InsufficientFundsException.class, () -> {
      wallet.debit(BigDecimal.ONE);
    });
  }
}

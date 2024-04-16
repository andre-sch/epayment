package com.moneytransactions.core;

import java.math.BigDecimal;
import com.moneytransactions.core.user.User;
import com.moneytransactions.core.wallet.Wallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
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

    assertThrows(ArithmeticException.class, () -> {
      wallet.debit(BigDecimal.ONE);
    });
  }
}

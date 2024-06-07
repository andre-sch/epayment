package com.epayment.core.unit;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.domain.exceptions.*;
import com.epayment.core.utils.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTests {
  private final BigDecimal zero = BigDecimal.valueOf(0L, 2);
  private final BigDecimal amount = BigDecimal.valueOf(1L, 2);
  private final DummyWalletFactory walletFactory = new DummyWalletFactory();

  @Test
  public void transactionSucceeds() {
    var receiver = walletFactory.build();
    var sender = walletFactory.build();

    var transaction = new Transaction();
    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(amount);
    transaction.execute();

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(receiver.getBalance()).isEqualTo(amount.add(amount));
    assertThat(sender.getBalance()).isEqualTo(zero);
  }

  @Test
  public void selfTransactionFails() {
    var wallet = new Wallet();

    assertThrows(SelfTransferException.class, () -> {
      var transaction = new Transaction();
      transaction.setEndpoints(wallet, wallet);
    });
  }
  
  @Test
  public void transactionFailsWithInvalidAmount() {
    assertThrows(InvalidTransactionAmountException.class, () -> {
      var transaction = new Transaction();
      transaction.setAmount(BigDecimal.ZERO);
    });
  }  
}

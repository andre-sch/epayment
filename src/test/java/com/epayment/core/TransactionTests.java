package com.epayment.core;

import java.math.BigDecimal;
import com.epayment.core.wallet.*;
import com.epayment.core.transaction.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTests {
  private final int senderId = 0;
  private final int receiverId = 1;

  @Test
  public void transactionSucceeds() {
    var sender = new Wallet(senderId);
    var receiver = new Wallet(receiverId);

    var transaction = new Transaction();
    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(BigDecimal.ONE);

    assertThat(transaction.getAmount()).isEqualTo(BigDecimal.ONE);
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
  public void negativeTransactionFails() {
    assertThrows(NegativeTransactionException.class, () -> {
      var transaction = new Transaction();
      transaction.setAmount(new BigDecimal(-1));
    });
  }  
}

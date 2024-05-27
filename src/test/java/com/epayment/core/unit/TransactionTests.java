package com.epayment.core.unit;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.exceptions.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTests {
  private final int senderId = 0;
  private final int receiverId = 1;
  private final BigDecimal amount = BigDecimal.ONE;

  @Test
  public void transactionSucceeds() {
    var receiver = new Wallet(receiverId);
    var sender = new Wallet(senderId);
    sender.credit(amount);

    var transaction = new Transaction();
    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(amount);
    transaction.execute();

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(receiver.getBalance()).isEqualTo(amount);
    assertThat(sender.getBalance()).isEqualTo(BigDecimal.ZERO);
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

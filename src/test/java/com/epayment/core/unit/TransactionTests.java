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
  private final DummyAccountFactory accountFactory = new DummyAccountFactory();

  @Test
  public void transactionSucceeds() {
    var receiver = accountFactory.build();
    var sender = accountFactory.build();

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
    var account = new Account();

    assertThrows(SelfTransferException.class, () -> {
      var transaction = new Transaction();
      transaction.setEndpoints(account, account);
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

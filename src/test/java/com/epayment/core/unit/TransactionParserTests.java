package com.epayment.core.unit;

import java.math.BigDecimal;
import com.epayment.core.utils.*;
import com.epayment.core.domain.*;
import static com.epayment.core.domain.BalanceChanged.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionParserTests {
  private final BigDecimal amount = BigDecimal.ONE;

  private final DummyAccountFactory accountFactory = new DummyAccountFactory();
  private final TransactionParser parser = new TransactionParser();

  @Test
  public void parsesTransactionBalanceChanges() {
    // arrange
    var sender = accountFactory.build();
    var receiver = accountFactory.build();
    
    var transaction = new Transaction();
    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(amount);
    
    // act
    var balanceChanges = parser.parseBalanceChangesOf(transaction);
    
    // assert
    assertThat(balanceChanges).contains(
      new BalanceChanged(
        amount.negate(),
        Endpoint.of(sender),
        Endpoint.of(receiver),
        transaction.getCompletedAt()
      )
    );

    assertThat(balanceChanges).contains(
      new BalanceChanged(
        amount,
        Endpoint.of(receiver),
        Endpoint.of(sender),
        transaction.getCompletedAt()
      )
    );
  }
}

package com.epayment.core.unit;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.utils.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionParserTests {
  private final BigDecimal amount = BigDecimal.ONE;

  private final DummyWalletFactory walletFactory = new DummyWalletFactory();
  private final TransactionParser parser = new TransactionParser();

  @Test
  public void parsesTransactionBalanceChanges() {
    // arrange
    var sender = walletFactory.build();
    var receiver = walletFactory.build();
    
    var transaction = new Transaction();
    transaction.setEndpoints(sender, receiver);
    transaction.setAmount(amount);
    
    // act
    var balanceChanges = parser.parseBalanceChangesOf(transaction);
    
    // assert
    assertThat(balanceChanges).contains(
      new BalanceChanged(
        amount.negate(),
        parser.buildEndpointOf(sender),
        parser.buildEndpointOf(receiver),
        transaction.getCompletedAt()
      )
    );

    assertThat(balanceChanges).contains(
      new BalanceChanged(
        amount,
        parser.buildEndpointOf(receiver),
        parser.buildEndpointOf(sender),
        transaction.getCompletedAt()
      )
    );
  }

  @Test
  public void endpointBuilding() {
    // arrange
    var user = new User();
    user.setFullName("owner");
    user.setEmail("owner@email");

    var wallet = new Wallet();
    wallet.setOwner(user);

    // act
    var endpoint = parser.buildEndpointOf(wallet);

    // assert
    assertThat(endpoint.email()).isEqualTo("owner@email");
    assertThat(endpoint.fullName()).isEqualTo("owner");
  }
}

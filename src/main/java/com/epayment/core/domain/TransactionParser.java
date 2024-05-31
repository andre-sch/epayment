package com.epayment.core.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class TransactionParser {
  private Transaction transaction;

  public List<BalanceChanged> parseBalanceChangesOf(Transaction transaction) {
    this.transaction = transaction;
    return Arrays.asList(
      parseSenderBalanceChanges(),
      parseReceiverBalanceChanges()
    );
  }

  private BalanceChanged parseSenderBalanceChanges() {
    return buildBalanceChanged(
      buildEndpointOf(transaction.getSender()),
      buildEndpointOf(transaction.getReceiver()),
      transaction.getAmount().negate(),
      transaction.getCompletedAt()
    );
  }

  private BalanceChanged parseReceiverBalanceChanges() {
    return buildBalanceChanged(
      buildEndpointOf(transaction.getReceiver()),
      buildEndpointOf(transaction.getSender()),
      transaction.getAmount(),
      transaction.getCompletedAt()
    );
  }

  private BalanceChanged buildBalanceChanged(
    BalanceChanged.Endpoint client,
    BalanceChanged.Endpoint partner,
    BigDecimal delta,
    Instant timestamp
  ) {
    return new BalanceChanged(delta, client, partner, timestamp);
  }

  private BalanceChanged.Endpoint buildEndpointOf(Wallet wallet) {
    return new BalanceChanged.Endpoint(
      wallet.getOwner().getEmail(),
      wallet.getOwner().getFullName()
    );
  }
}

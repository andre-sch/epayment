package com.epayment.core.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.epayment.core.domain.events.BalanceChanged;
import static com.epayment.core.domain.events.BalanceChanged.Endpoint;

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
      Endpoint.of(transaction.getSender()),
      Endpoint.of(transaction.getReceiver()),
      transaction.getAmount().negate(),
      transaction.getCompletedAt()
    );
  }

  private BalanceChanged parseReceiverBalanceChanges() {
    return buildBalanceChanged(
      Endpoint.of(transaction.getReceiver()),
      Endpoint.of(transaction.getSender()),
      transaction.getAmount(),
      transaction.getCompletedAt()
    );
  }

  private BalanceChanged buildBalanceChanged(
    Endpoint client,
    Endpoint partner,
    BigDecimal delta,
    Instant timestamp
  ) {
    return new BalanceChanged(delta, client, partner, timestamp);
  }
}

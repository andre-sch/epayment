package com.epayment.core.domain;

import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;

import com.epayment.core.domain.events.BalanceChanged;
import com.epayment.core.domain.exceptions.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
public class Transaction {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter int id;
  
  private @ManyToOne @Getter Account sender;
  private @ManyToOne @Getter Account receiver;
  
  private @Getter BigDecimal amount;
  private @Getter Instant completedAt;

  private @Transient TransactionParser transactionParser = new TransactionParser();
  private @Transient @Getter List<BalanceChanged> events = new LinkedList<>();

  public void execute() {
    this.sender.debit(amount);
    this.receiver.credit(amount);
    this.completedAt = Instant.now();
    this.transactionParser
      .parseBalanceChangesOf(this)
      .forEach(this.events::add);
  }

  public void setEndpoints(Account sender, Account receiver) {
    if (Objects.equals(sender, receiver)) {
      throw new SelfTransferException("sender cannot be receiver");
    }

    this.sender = sender;
    this.receiver = receiver;
  }

  public void setAmount(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidTransactionAmountException("transaction must be positive");
    }
    
    this.amount = amount;
  }
}

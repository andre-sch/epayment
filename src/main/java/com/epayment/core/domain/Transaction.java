package com.epayment.core.domain;

import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;

import com.epayment.core.exceptions.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
public class Transaction {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter int id;
  
  @ManyToOne
  private @Getter Wallet sender;
  
  @ManyToOne
  private @Getter Wallet receiver;
  
  private @Getter BigDecimal amount;
  private @Getter Instant completedAt;

  @Transient TransactionParser transactionParser = new TransactionParser();
  @Transient private List<BalanceChanged> events = new LinkedList<>();

  public void execute() {
    this.sender.debit(amount);
    this.receiver.credit(amount);
    this.completedAt = Instant.now();
    this.transactionParser
      .parseBalanceChangesOf(this)
      .forEach(this.events::add);
  }

  public List<BalanceChanged> getEvents() { return this.events; }

  public void setEndpoints(Wallet sender, Wallet receiver) {
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

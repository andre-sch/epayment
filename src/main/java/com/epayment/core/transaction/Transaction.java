package com.epayment.core.transaction;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.wallet.Wallet;
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
  private @Getter Instant executedAt;

  public Transaction() {
    this.executedAt = Instant.now();
  }

  public void setEndpoints(Wallet sender, Wallet receiver) {
    if (sender.equals(receiver)) {
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

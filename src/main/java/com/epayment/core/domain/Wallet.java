package com.epayment.core.domain;

import java.math.BigDecimal;
import com.epayment.core.domain.exceptions.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wallets")
public class Wallet {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter int id;
  private @Getter BigDecimal balance = BigDecimal.ZERO;

  @ManyToOne
  private @Getter @Setter User owner;

  public void credit(BigDecimal value) {
    this.balance = this.balance.add(value);
  }

  public void debit(BigDecimal value) {
    if (this.balance.compareTo(value) < 0) {
      throw new InsufficientFundsException("balance cannot be negative");
    }

    this.balance = this.balance.subtract(value);
  }
}

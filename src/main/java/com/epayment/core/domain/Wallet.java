package com.epayment.core.domain;

import java.math.BigDecimal;
import com.epayment.core.exceptions.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wallets")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Wallet {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private @Getter int id;
  private @Getter BigDecimal balance = BigDecimal.ZERO;

  @ManyToOne
  private @Getter @Setter User owner;

  public Wallet() {}
  public Wallet(int id) {
    this.id = id;
  }

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

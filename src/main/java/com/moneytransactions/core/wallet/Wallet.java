package com.moneytransactions.core.wallet;

import java.math.BigDecimal;
import com.moneytransactions.core.user.User;
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

  public Wallet() {}
  public Wallet(int id) {
    this.id = id;
  }

  public void credit(BigDecimal value) {
    balance = balance.add(value);
  }

  public void debit(BigDecimal value) {
    if (balance.compareTo(value) < 0) {
      throw new InsufficientFundsException("balance cannot be negative");
    }

    balance = balance.subtract(value);
  }
}

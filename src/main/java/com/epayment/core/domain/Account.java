package com.epayment.core.domain;

import java.util.*;
import java.math.BigDecimal;
import com.epayment.core.domain.exceptions.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@EqualsAndHashCode
public class Account {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter int id;
  
  private @Getter BigDecimal balance = BigDecimal.ZERO;
  
  @Column(unique = true)
  private @Getter @Setter String email;
  private @Getter @Setter String password;
  private @Getter @Setter String fullName;

  private @Transient @Getter List<AccountCreated> events = new LinkedList<>();

  public void recordChanges() {
    this.events.add(AccountCreated.of(this));
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

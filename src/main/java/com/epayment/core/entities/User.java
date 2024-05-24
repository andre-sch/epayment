package com.epayment.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@EqualsAndHashCode
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter int id;
  
  @Column(unique = true)
  private @Getter @Setter String document;
  private @Getter @Setter String fullName;
  
  @Column(unique = true)
  private @Getter @Setter String email;
  private @Getter @Setter String password;

  public User() {}
  public User(int id) {
    this.id = id;
  }
}

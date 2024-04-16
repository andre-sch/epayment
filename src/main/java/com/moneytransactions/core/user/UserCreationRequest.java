package com.moneytransactions.core.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
  public String document;
  public String fullName;
  public String email;
  public String password;
}

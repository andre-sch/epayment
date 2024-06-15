package com.epayment.core.application.services.createUser;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
  public String document;
  public String fullName;
  public String email;
  public String password;
}

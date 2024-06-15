package com.epayment.core.utils;

import com.epayment.core.domain.User;
import com.epayment.core.application.services.createUser.UserCreationRequest;

public class DummyUserFactory extends DummyFactory<User> {
  public User build() {
    var user = new User();

    user.setDocument(dummy());
    user.setFullName(dummy());
    user.setEmail(dummy());
    user.setPassword(dummy());

    return user;
  }

  public UserCreationRequest buildRequest() {
    var user = build();
    return new UserCreationRequest(
      user.getDocument(),
      user.getFullName(),
      user.getEmail(),
      user.getPassword()
    );
  }
}

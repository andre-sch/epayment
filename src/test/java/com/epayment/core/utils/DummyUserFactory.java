package com.epayment.core.utils;

import com.epayment.core.domain.User;
import com.epayment.core.application.services.*;

public class DummyUserFactory extends DummyFactory<User> {
  public User build() {
    var user = new User();

    user.setDocument(dummy());
    user.setFullName(dummy());
    user.setEmail(dummy());
    user.setPassword(dummy());

    return user;
  }

  public CreateUserService.Request buildRequest() {
    var user = build();
    return new CreateUserService.Request(
      user.getDocument(),
      user.getFullName(),
      user.getEmail(),
      user.getPassword()
    );
  }
}

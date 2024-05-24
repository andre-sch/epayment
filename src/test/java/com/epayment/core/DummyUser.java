package com.epayment.core;

import java.util.UUID;
import com.epayment.core.entities.User;
import com.epayment.core.services.createUser.UserCreationRequest;

public class DummyUser {
  public static User get() {
    var user = new User();

    String credential = UUID.randomUUID().toString();

    user.setDocument(credential);
    user.setFullName(credential);
    user.setEmail(credential);
    user.setPassword(credential);

    return user;
  }

  public static UserCreationRequest getCreationRequest() {
    String credential = UUID.randomUUID().toString();
    return new UserCreationRequest(credential, credential, credential, credential);
  }
}

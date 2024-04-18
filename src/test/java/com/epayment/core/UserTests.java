package com.epayment.core;

import com.epayment.core.entities.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {
  private final String document = "identity";
  private final String fullName = "John Doe";
  private final String email = "john@doe";
  private final String password = "123";

  @Test
  public void userCreation() {
    var user = new User();

    user.setDocument(document);
    user.setFullName(fullName);
    user.setEmail(email);
    user.setPassword(password);

    assertThat(user.getDocument()).isEqualTo(document);
    assertThat(user.getFullName()).isEqualTo(fullName);
  }
}

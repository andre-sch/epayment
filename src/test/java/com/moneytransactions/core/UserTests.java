package com.moneytransactions.core;

import com.moneytransactions.core.user.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTests {
  private final String document = "identity";
  private final String fullName = "John Doe";
  private final String email = "john@doe";
  private final String password = "123";

  @Test
  public void userCreation() {
    var user = new User();

    user.setDocument(this.document);
    user.setFullName(this.fullName);
    user.setEmail(this.email);
    user.setPassword(this.password);

    assertThat(user.getDocument()).isEqualTo(this.document);
    assertThat(user.getFullName()).isEqualTo(this.fullName);
  }
}

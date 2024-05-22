package com.epayment.core.user;

import com.epayment.core.services.createUser.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreateUserServiceTests {
  private final String document = "identity";
  private final String fullName = "John Doe";
  private final String email = "john@doe";
  private final String password = "123";

  @Autowired private CreateUserService createUserService;

  @Test
  public void userCreationSucceeds() {
    var request = new UserCreationRequest(document, fullName, email, password);

    var user = this.createUserService.execute(request);

    assertThat(user.getEmail()).isEqualTo(email);
    assertThat(user.getPassword()).isNotEqualTo(password);
  }
}

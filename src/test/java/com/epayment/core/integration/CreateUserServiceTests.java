package com.epayment.core.integration;

import com.epayment.core.utils.DummyUserFactory;
import com.epayment.core.services.createUser.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreateUserServiceTests {
  private final DummyUserFactory userFactory = new DummyUserFactory();
  @Autowired private CreateUserService createUserService;

  @Test
  public void userCreationSucceeds() {
    var request = userFactory.buildRequest();

    var user = this.createUserService.execute(request);

    assertThat(user.getEmail()).isEqualTo(request.email);
    assertThat(user.getPassword()).isNotEqualTo(request.password);
  }
}

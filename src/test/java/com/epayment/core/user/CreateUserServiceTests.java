package com.epayment.core.user;

import com.epayment.core.DummyUser;
import com.epayment.core.services.createUser.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreateUserServiceTests {
  @Autowired private CreateUserService createUserService;

  @Test
  public void userCreationSucceeds() {
    var request = DummyUser.getCreationRequest();

    var user = this.createUserService.execute(request);

    assertThat(user.getEmail()).isEqualTo(request.email);
    assertThat(user.getPassword()).isNotEqualTo(request.password);
  }
}

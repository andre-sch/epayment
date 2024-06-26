package com.epayment.core.user;

import com.epayment.core.repositories.*;
import com.epayment.core.services.createUser.*;
import com.epayment.core.exceptions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreateUserServiceTests {
  private final String document = "identity";
  private final String fullName = "John Doe";
  private final String email = "john@doe";
  private final String password = "123";

  @Autowired private UserRepository userRepository;
  @Autowired private CreateUserService createUserService;

  @BeforeEach
  public void cleanUsersRepository() {
    this.userRepository.deleteAll();
  }

  @Test
  public void userCreationSucceeds() {
    var request = getCreationRequest();

    var user = this.createUserService.execute(request);

    assertThat(user.getEmail()).isEqualTo(email);
    assertThat(user.getPassword()).isNotEqualTo(password);
  }

  @Test
  public void userCreationFailsWhenEmailAlreadyInUse() {
    var request = getCreationRequest();
    this.createUserService.execute(request);

    assertThrows(DuplicateUserException.class, () -> {
      this.createUserService.execute(request);
    });
  }

  private UserCreationRequest getCreationRequest() {
    return new UserCreationRequest(document, fullName, email, password);
  }
}

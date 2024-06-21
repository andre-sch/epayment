package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.utils.DummyAccountFactory;
import com.epayment.core.application.services.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreateAccountServiceTests {
  private final BigDecimal initialCredit = BigDecimal.valueOf(10000L, 2);
  private final DummyAccountFactory accountFactory = new DummyAccountFactory();
  @Autowired private CreateAccountService createAccountService;

  @Test
  public void accountCreationSucceeds() {
    var request = accountFactory.buildRequest();

    var account = this.createAccountService.execute(request);

    assertThat(account.getEmail()).isEqualTo(request.email());
    assertThat(account.getPassword()).isNotEqualTo(request.password());
    assertThat(account.getFullName()).isEqualTo(request.fullName());
    assertThat(account.getBalance()).isEqualTo(initialCredit);
  }
}

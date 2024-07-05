package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.DomainLogger;
import com.epayment.core.domain.AccountCreated;
import com.epayment.core.adapters.kafka.AccountCreatedKafkaLogger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SpringBootTest
@ActiveProfiles("test")
public class AccountCreatedKafkaLoggerTests {
  @MockBean private DomainLogger logger;
  @Autowired private AccountCreatedKafkaLogger eventLogger;

  @Test
  public void logging() {
    int accountId = 1;
    var event = new AccountCreated(
      accountId,
      "client@email",
      "clientName",
      BigDecimal.valueOf(1001L, 2)
    );

    eventLogger.log(event);

    Mockito.verify(logger).info(
      "com.epayment.core.domain.AccountCreated:" +
      "{\"id\":1,\"balance\":10.01}"
    );
  }
}

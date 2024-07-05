package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.DomainLogger;
import com.epayment.core.domain.AccountDeleted;
import com.epayment.core.adapters.log.AccountDeletedKafkaLogger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SpringBootTest
@ActiveProfiles("test")
public class AccountDeletedKafkaLoggerTests {
  @MockBean private DomainLogger logger;
  @Autowired private AccountDeletedKafkaLogger eventLogger;

  @Test
  public void logging() {
    int accountId = 1;
    var event = new AccountDeleted(
      accountId,
      "client@email",
      "clientName",
      BigDecimal.valueOf(1001L, 2)
    );

    eventLogger.log(event);

    Mockito.verify(logger).info(
      "com.epayment.core.domain.AccountDeleted:" +
      "{\"id\":1,\"oldBalance\":10.01}"
    );
  }
}

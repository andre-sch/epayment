package com.epayment.core.integration;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.domain.DomainLogger;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.adapters.sub.log.BalanceChangedKafkaLogger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SpringBootTest
@ActiveProfiles("test")
public class BalanceChangedKafkaLoggerTests {
  @MockBean private DomainLogger logger;
  @Autowired private BalanceChangedKafkaLogger eventLogger;

  @Test
  public void logging() {
    var event = new BalanceChanged(
      BigDecimal.valueOf(1001L, 2),
      new BalanceChanged.Endpoint(1, "client@email", "clientName"),
      new BalanceChanged.Endpoint(2, "partner@email", "partnerName"),
      Instant.ofEpochMilli(0L)
    );

    eventLogger.log(event);

    Mockito.verify(logger).info(
      "com.epayment.core.domain.BalanceChanged:" +
      "{\"delta\":10.01,\"client\":1,\"partner\":2,\"timestamp\":0.0}"
    );
  }
}

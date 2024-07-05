package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.DomainLogger;
import com.epayment.core.domain.TransactionFailed;
import com.epayment.core.adapters.log.TransactionFailedKafkaLogger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionFailedKafkaLoggerTests {
  @MockBean private DomainLogger logger;
  @Autowired private TransactionFailedKafkaLogger eventLogger;

  @Test
  public void logging() {
    var event = new TransactionFailed(
      BigDecimal.valueOf(1001L, 2),
      new TransactionFailed.Endpoint(1, "sender@email", "senderName"),
      new TransactionFailed.Endpoint(2, "receiver@email", "receiverName")
    );

    eventLogger.log(event);

    Mockito.verify(logger).info(
      "com.epayment.core.domain.TransactionFailed:" +
      "{\"amount\":10.01,\"sender\":1,\"receiver\":2}"
    );
  }
}

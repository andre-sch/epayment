package com.epayment.core.integration;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.adapters.kafka.KafkaProducer;
import com.epayment.core.application.interfaces.JsonConverter;
import com.epayment.core.domain.BalanceChanged;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class BalanceChangedKafkaProducerTests {
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;
  @Autowired private KafkaProducer<BalanceChanged> kafkaProducer;
  @Autowired private JsonConverter json;

  @Test
  public void sendingOfMessages() {
    var event = new BalanceChanged(
      BigDecimal.ONE,
      new BalanceChanged.Endpoint(1, "client@email", "clientName"),
      new BalanceChanged.Endpoint(2, "partner@email", "partnerName"),
      Instant.now()
    );

    kafkaProducer.accept(event);

    verify(kafkaTemplate).send("balances", "client@email", json.serialize(event));
  }
}

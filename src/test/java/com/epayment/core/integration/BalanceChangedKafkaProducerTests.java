package com.epayment.core.integration;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.adapters.KafkaProducer;
import com.epayment.core.application.interfaces.JsonConverter;
import com.epayment.core.domain.BalanceChanged;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BalanceChangedKafkaProducerTests {
  @MockBean private KafkaTemplate<String, String> kafkaTemplate;
  @Autowired private KafkaProducer<BalanceChanged> kafkaProducer;
  @Autowired private JsonConverter json;

  @Test
  public void sendingOfMessages() {
    var event = new BalanceChanged(
      BigDecimal.ONE,
      new BalanceChanged.Endpoint("client@email", "clientName"),
      new BalanceChanged.Endpoint("partner@email", "partnerName"),
      Instant.now()
    );

    kafkaProducer.accept(event);

    verify(kafkaTemplate).send("balances", "client@email", json.serialize(event));
  }
}

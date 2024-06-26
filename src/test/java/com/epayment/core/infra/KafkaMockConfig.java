package com.epayment.core.infra;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.test.mock.mockito.MockBean;

@Configuration
public class KafkaMockConfig {
  @MockBean public KafkaTemplate<String, String> kafkaTemplate;
}

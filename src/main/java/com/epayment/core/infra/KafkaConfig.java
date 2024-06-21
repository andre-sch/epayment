package com.epayment.core.infra;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.*;

@Configuration
public class KafkaConfig {
  @Bean
  public NewTopic balancesTopic() {
    return TopicBuilder.name("balances").build();
  }
}

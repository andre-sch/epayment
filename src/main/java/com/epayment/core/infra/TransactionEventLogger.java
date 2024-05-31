package com.epayment.core.infra;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventLogger {
  @KafkaListener(topics = {"balances"}, groupId = "logger")
  public void log(String event) {
    System.out.println(event);
  }
}

package com.epayment.core.adapters;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BalanceChangedLogger {
  @KafkaListener(topics = {"balances"}, groupId = "logger")
  public void log(String event) {
    log.info(event);
  }
}

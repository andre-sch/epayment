package com.epayment.core.adapters.kafka;

import com.epayment.core.domain.AccountEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountEventKafkaProducer extends KafkaProducer<AccountEvent> {
  protected String topicOf(AccountEvent event) { return "accounts"; }
  protected String keyOf(AccountEvent event) { return event.email(); }
}

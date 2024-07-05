package com.epayment.core.adapters.pub;

import com.epayment.core.domain.events.AccountEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountEventKafkaProducer extends KafkaProducer<AccountEvent> {
  protected String topicOf(AccountEvent event) { return "accounts"; }
  protected String keyOf(AccountEvent event) { return event.email(); }
}

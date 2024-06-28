package com.epayment.core.adapters.kafka;

import com.epayment.core.domain.AccountCreated;
import org.springframework.stereotype.Component;

@Component
public class AccountCreatedKafkaProducer extends KafkaProducer<AccountCreated> {
  protected String topicOf(AccountCreated event) { return "accounts"; }
  protected String keyOf(AccountCreated event) { return event.email(); }
}

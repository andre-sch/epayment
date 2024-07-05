package com.epayment.core.adapters.pub;

import com.epayment.core.domain.BalanceChanged;
import org.springframework.stereotype.Component;

@Component
public class BalanceChangedKafkaProducer extends KafkaProducer<BalanceChanged> {
  protected String topicOf(BalanceChanged event) { return "balances"; }
  protected String keyOf(BalanceChanged event) { return event.client().email(); }
}

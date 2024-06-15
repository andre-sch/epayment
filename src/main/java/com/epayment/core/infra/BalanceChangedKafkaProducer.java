package com.epayment.core.infra;

import com.epayment.core.domain.BalanceChanged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceChangedKafkaProducer extends KafkaProducer<BalanceChanged> {
  @Autowired private JsonConverter json;

  protected String topicOf(BalanceChanged event) {
    return "balances";
  }

  protected String keyOf(BalanceChanged event) {
    return event.client().email();
  }

  protected String valueOf(BalanceChanged event) {
    return json.serialize(event);
  }
}

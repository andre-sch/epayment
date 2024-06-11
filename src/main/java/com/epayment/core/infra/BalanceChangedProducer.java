package com.epayment.core.infra;

import com.epayment.core.domain.BalanceChanged;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceChangedProducer extends KafkaProducer<BalanceChanged> {
  @Autowired private ObjectMapper mapper;

  protected String topicOf(BalanceChanged event) {
    return "balances";
  }

  protected String keyOf(BalanceChanged event) {
    return event.client().email();
  }

  protected String valueOf(BalanceChanged event) {
    try { return mapper.writeValueAsString(event); }
    catch (Exception e) { throw new RuntimeException(e); }
  }
}

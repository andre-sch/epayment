package com.epayment.core.infra;

import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.domain.EventDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventDispatcher implements EventDispatcher<BalanceChanged> {
  @Autowired private KafkaTemplate<String, String> kafka;
  @Autowired private ObjectMapper mapper;

  public void dispatch(BalanceChanged event) {
    kafka.send("balances", keyOf(event), valueOf(event));
  }

  public String keyOf(BalanceChanged event) {
    return event.client().email();
  }

  public String valueOf(BalanceChanged event) {
    try { return mapper.writeValueAsString(event); }
    catch (Exception e) { throw new RuntimeException(e); }
  }
}

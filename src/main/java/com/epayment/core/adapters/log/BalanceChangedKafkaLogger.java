package com.epayment.core.adapters.log;

import java.math.BigDecimal;
import java.time.Instant;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceChangedKafkaLogger extends EventLogger<BalanceChanged> {
  @Autowired private JsonConverter json;

  @KafkaListener(topics = "balances", groupId = "balances_logger")
  public void log(String serializedEvent) {
    var event = json.deserialize(serializedEvent, BalanceChanged.class);
    super.log(event);
  }

  public BalanceChangeFiltered filterSensitiveData(BalanceChanged event) {
    return new BalanceChangeFiltered(
      event.delta(),
      event.client().id(),
      event.partner().id(),
      event.timestamp()
    );
  }

  public static record BalanceChangeFiltered(
    BigDecimal delta,
    int client,
    int partner,
    Instant timestamp
  ) {}
}

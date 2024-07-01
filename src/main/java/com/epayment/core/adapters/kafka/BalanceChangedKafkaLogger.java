package com.epayment.core.adapters.kafka;

import java.math.BigDecimal;
import java.time.Instant;
import com.epayment.core.domain.BalanceChanged;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BalanceChangedKafkaLogger extends EventLogger {
  public BalanceChangedKafkaLogger() {
    super(new DataFilter[] { new BalanceChangedDataFilter() });
  }

  @KafkaListener(topics = "balances", groupId = "balances_logger")
  public void log(String serializedEvent) {
    super.log(serializedEvent);
  }
}

class BalanceChangedDataFilter implements EventLogger.DataFilter {
  public Class<?> getInputClass() {
    return BalanceChanged.class;
  }

  public BalanceChangeFiltered filter(Object input) {
    var event = (BalanceChanged) input;
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

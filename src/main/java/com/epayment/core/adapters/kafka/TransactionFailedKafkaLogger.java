package com.epayment.core.adapters.kafka;

import java.math.BigDecimal;
import com.epayment.core.domain.TransactionFailed;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionFailedKafkaLogger extends EventLogger {
  public TransactionFailedKafkaLogger() {
    super(new DataFilter[] { new TransactionFailedDataFilter() });
  }

  @KafkaListener(topics = "transactions", groupId = "transactions_logger")
  public void log(String serializedEvent) {
    super.log(serializedEvent);
  }
}

class TransactionFailedDataFilter implements EventLogger.DataFilter {
  public Class<?> getInputClass() {
    return TransactionFailed.class;
  }

  public TransactionFailureFiltered filter(Object input) {
    var event = (TransactionFailed) input;
    return new TransactionFailureFiltered(
      event.amount(),
      event.sender().id(),
      event.receiver().id()
    );
  }

  public static record TransactionFailureFiltered(
    BigDecimal amount,
    int sender,
    int receiver
  ) {}
}

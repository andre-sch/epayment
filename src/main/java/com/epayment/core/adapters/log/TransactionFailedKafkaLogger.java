package com.epayment.core.adapters.log;

import java.math.BigDecimal;
import com.epayment.core.domain.TransactionFailed;
import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionFailedKafkaLogger extends EventLogger<TransactionFailed> {
  @Autowired private JsonConverter json;

  @KafkaListener(topics = "transactions", groupId = "transactions_logger")
  public void log(String serializedEvent) {
    var event = json.deserialize(serializedEvent, TransactionFailed.class);
    super.log(event);
  }

  public TransactionFailureFiltered filterSensitiveData(TransactionFailed event) {
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

package com.epayment.core.adapters.kafka;

import com.epayment.core.domain.TransactionFailed;
import org.springframework.stereotype.Component;

@Component
public class TransactionFailedKafkaProducer extends KafkaProducer<TransactionFailed> {
  protected String topicOf(TransactionFailed event) { return "transactions"; }
}

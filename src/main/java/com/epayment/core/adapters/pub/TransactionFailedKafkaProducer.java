package com.epayment.core.adapters.pub;

import com.epayment.core.domain.events.TransactionFailed;
import org.springframework.stereotype.Component;

@Component
public class TransactionFailedKafkaProducer extends KafkaProducer<TransactionFailed> {
  protected String topicOf(TransactionFailed event) { return "transactions"; }
}

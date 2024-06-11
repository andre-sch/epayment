package com.epayment.core.infra;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class KafkaProducer<Event> implements Consumer<Event> {
  @Autowired private KafkaTemplate<String, String> kafka;

  public void accept(Event event) {
    kafka.send(
      topicOf(event),
      keyOf(event),
      valueOf(event)
    );
  }

  protected abstract String topicOf(Event event);
  protected abstract String keyOf(Event event);
  protected abstract String valueOf(Event event);
}

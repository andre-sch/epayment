package com.epayment.core.adapters.pub;

import java.util.function.Consumer;
import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class KafkaProducer<Event> implements Consumer<Event> {
  @Autowired private KafkaTemplate<String, String> kafka;
  @Autowired private JsonConverter json;

  public void accept(Event event) {
    kafka.send(
      topicOf(event),
      keyOf(event),
      valueOf(event)
    );
  }

  protected abstract String topicOf(Event event);
  protected String keyOf(Event event) { return null; }
  protected String valueOf(Event event) { return json.serialize(event); }
}

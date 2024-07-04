package com.epayment.core.adapters.kafka;

import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class EventLogger<Event> {
  private final String delimiter = ":";
  @Autowired private JsonConverter json;

  public void log(Event event) {
    var eventName = event.getClass().getName();
    var filteredEvent = filterSensitiveData(event);

    log.info(String.join(delimiter, eventName, json.serialize(filteredEvent)));
  }

  protected abstract Object filterSensitiveData(Event event);
}

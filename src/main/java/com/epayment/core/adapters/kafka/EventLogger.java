package com.epayment.core.adapters.kafka;

import com.epayment.core.domain.DomainLogger;
import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class EventLogger<Event> {
  private final String delimiter = ":";
  @Autowired private JsonConverter json;
  @Autowired private DomainLogger log;

  public void log(Event event) {
    var eventName = event.getClass().getName();
    var filteredEvent = filterSensitiveData(event);

    log.info(String.join(delimiter, eventName, json.serialize(filteredEvent)));
  }

  protected abstract Object filterSensitiveData(Event event);
}

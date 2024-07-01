package com.epayment.core.adapters.kafka;

import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class EventLogger {
  private final DataFilter[] eventDataFilters;
  @Autowired private JsonConverter json;

  public EventLogger(DataFilter[] eventDataFilters) {
    this.eventDataFilters = eventDataFilters;
  }

  public void log(String serializedEvent) {
    for (var eventDataFilter : eventDataFilters) {
      try {
        var event = json.deserialize(serializedEvent, eventDataFilter.getInputClass());
        var filteredEvent = eventDataFilter.filter(event);
        log.info(json.serialize(filteredEvent));
      } catch (Exception e) {}
    }
  }

  public interface DataFilter {
    public Object filter(Object input);
    public Class<?> getInputClass();
  }
}
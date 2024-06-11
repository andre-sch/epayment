package com.epayment.core.domain;

import java.util.List;
import java.util.LinkedList;
import java.util.function.Consumer;

public class EventDispatcher<Event> {
  private List<Consumer<Event>> consumers = new LinkedList<>();

  public void attach(Consumer<Event> consumer) {
    consumers.add(consumer);
  }

  public void dispatch(Event event) {
    consumers.forEach(consumer -> consumer.accept(event));
  }
}

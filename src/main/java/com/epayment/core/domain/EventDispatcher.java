package com.epayment.core.domain;

public interface EventDispatcher<T> {
  public void dispatch(T event);
}

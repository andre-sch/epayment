package com.epayment.core.utils;

import java.util.UUID;

public abstract class DummyFactory<Type> {
  public abstract Type build();
  protected String dummy() {
    return UUID
      .randomUUID()
      .toString();
  }
}

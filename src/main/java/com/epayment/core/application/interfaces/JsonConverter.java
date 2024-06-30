package com.epayment.core.application.interfaces;

public interface JsonConverter {
  public String serialize(Object input);
  public <T> T deserialize(String input, Class<T> clazz);
  public <T> boolean match(String input, Class<T> clazz);
}
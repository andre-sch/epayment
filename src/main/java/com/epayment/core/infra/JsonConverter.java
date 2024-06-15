package com.epayment.core.infra;

public interface JsonConverter {
  public String serialize(Object input);
  public <T> T deserialize(String input, Class<T> clazz);
}
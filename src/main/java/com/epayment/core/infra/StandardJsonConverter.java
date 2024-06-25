package com.epayment.core.infra;

import com.epayment.core.domain.exceptions.OperationalException;
import com.epayment.core.application.interfaces.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class StandardJsonConverter implements JsonConverter {
  private ObjectMapper objectMapper;

  public StandardJsonConverter() {
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
  }

  public String serialize(Object input) {
    try { return objectMapper.writeValueAsString(input); }
    catch (Exception e) { throw new OperationalException("json is not serializable"); }
  }

  public <T> T deserialize(String input, Class<T> clazz) {
    try { return objectMapper.readValue(input, clazz); }
    catch (Exception e) { throw new OperationalException("json is not deserializable"); }
  }
}

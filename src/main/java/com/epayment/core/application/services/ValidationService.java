package com.epayment.core.application.services;

import java.util.*;
import jakarta.validation.Validator;
import com.epayment.core.domain.exceptions.OperationalException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
  private Validator validator;

  public ValidationService(Validator validator) {
    this.validator = validator;
  }

  public <T> void execute(T request) {
    List<String> constraints = new LinkedList<>();
    
    var violations = this.validator.validate(request);
    if (violations.isEmpty()) return;

    final String delimiter = " ";

    for (var violation : violations) {
      String fieldName = violation.getPropertyPath().toString();
      String constraint = violation.getMessage();
      constraints.add(String.join(delimiter, fieldName, constraint));
    }

    throw new OperationalException("validation failed", constraints);
  }
}

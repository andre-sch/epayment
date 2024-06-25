package com.epayment.core.adapters.web;

import com.epayment.core.domain.exceptions.OperationalException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@ControllerAdvice
public class ExceptionHandlingController {
  @ExceptionHandler({ OperationalException.class })
  public ResponseEntity<Feedback> handleOperationalException(OperationalException exception) {
    return new ResponseEntity<>(Feedback.from(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Feedback> handleException(Exception exception) {
    exception.printStackTrace();
    var genericFeedback = new Feedback("internal server error");
    return new ResponseEntity<>(genericFeedback, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

record Feedback(String message, Object body) {
  public Feedback(String message) {
    this(message, null);
  }

  public static Feedback from(Exception exception) {
    return new Feedback(exception.getMessage());
  }

  public static Feedback from(OperationalException exception) {
    return new Feedback(exception.getMessage(), exception.getBody());
  }
}

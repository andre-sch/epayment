package com.epayment.core.adapters.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@ControllerAdvice
public class ExceptionHandlingController {
  @ExceptionHandler({ RuntimeException.class })
  public ResponseEntity<Feedback> handleRuntimeException(RuntimeException exception) {
    return new ResponseEntity<>(Feedback.from(exception), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Feedback> handleException(Exception exception) {
    return new ResponseEntity<>(Feedback.from(exception), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

record Feedback(String message) {
  public static Feedback from(Exception exception) {
    return new Feedback(exception.getMessage());
  }
}

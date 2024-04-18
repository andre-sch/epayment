package com.epayment.core.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionHandlingController {
  @ExceptionHandler(RequestException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public String handleRequestException(RequestException exception) {
    return exception.getMessage();
  }
}

package com.epayment.core.adapters.web;

import jakarta.validation.constraints.*;
import com.epayment.core.application.services.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
public class DeleteAccountController {
  private DeleteAccountService deleteAccountService;
  private ValidationService validationService;
  
  public DeleteAccountController(
    DeleteAccountService deleteAccountService,
    ValidationService validationService
  ) {
    this.deleteAccountService = deleteAccountService;
    this.validationService = validationService;
  }

  @DeleteMapping("/account")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void handle(@RequestBody Request request) {
    this.validationService.execute(request);
    this.deleteAccountService.execute(request.email);
  }

  public static record Request(@NotNull @Email String email) {}
}

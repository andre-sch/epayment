package com.epayment.core.adapters.web;

import com.epayment.core.application.services.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
public class DeleteAccountController {
  private DeleteAccountService deleteAccountService;

  public DeleteAccountController(
    DeleteAccountService deleteAccountService
  ) {
    this.deleteAccountService = deleteAccountService;
  }

  @DeleteMapping("/account")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void handle(@RequestBody Request request) {
    this.deleteAccountService.execute(request.email);
  }

  public static record Request(String email) {}
}

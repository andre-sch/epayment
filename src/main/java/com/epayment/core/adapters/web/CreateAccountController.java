package com.epayment.core.adapters.web;

import java.math.BigDecimal;
import com.epayment.core.application.services.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
public class CreateAccountController {
  private CreateAccountService createAccountService;

  public CreateAccountController(
    CreateAccountService createAccountService
  ) {
    this.createAccountService = createAccountService;
  }

  @PostMapping("/account")
  @ResponseStatus(code = HttpStatus.CREATED)
  @ResponseBody
  public AccountView handle(@RequestBody CreateAccountService.Request request) {
    var createdAccount = this.createAccountService.execute(request);

    return new AccountView(
      createdAccount.getFullName(),
      createdAccount.getEmail(),
      createdAccount.getBalance()
    );
  }
}

record AccountView(String fullname, String email, BigDecimal balance) {}

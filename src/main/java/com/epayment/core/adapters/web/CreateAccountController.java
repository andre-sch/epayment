package com.epayment.core.adapters.web;

import java.math.BigDecimal;
import com.epayment.core.application.services.createUser.*;
import com.epayment.core.application.services.createWallet.CreateWalletService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateAccountController {
  private CreateUserService createUserService;
  private CreateWalletService createWalletService;

  public CreateAccountController(
    CreateUserService createUserService,
    CreateWalletService createWalletService
  ) {
    this.createUserService = createUserService;
    this.createWalletService = createWalletService;
  }

  @PostMapping("/account")
  @ResponseBody
  public WalletView handle(@RequestBody CreateUserService.Request request) {
    var createdUser = this.createUserService.execute(request);
    var createdWallet = this.createWalletService.execute(createdUser.getId());

    return new WalletView(
      createdUser.getFullName(),
      createdUser.getEmail(),
      createdWallet.getBalance()
    );
  }
}

record WalletView(String fullname, String email, BigDecimal balance) {}

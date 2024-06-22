package com.epayment.core.adapters.web;

import java.math.BigDecimal;
import com.epayment.core.application.repositories.AccountRepository;
import com.epayment.core.domain.Account;

import org.springframework.web.bind.annotation.*;

@RestController
public class ListAccountDetails {
  private AccountRepository accountRepository;

  public ListAccountDetails(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/account")
  @ResponseBody
  public AccountDetails handle(@RequestBody Request request) {
    var accountQuery = accountRepository.findByEmail(request.email);

    if (accountQuery.isEmpty()) {
      throw new RuntimeException("account does not exist");
    }

    var account = accountQuery.get();
    return AccountDetails.of(account);
  }

  public static record Request(String email) {}
}

record AccountDetails(
  String email,
  String fullName,
  BigDecimal balance
) {
  public static AccountDetails of(Account account) {
    return new AccountDetails(
      account.getEmail(),
      account.getFullName(),
      account.getBalance()
    );
  }
}

package com.epayment.core.adapters.web;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import com.epayment.core.domain.Account;
import com.epayment.core.domain.exceptions.OperationalException;
import com.epayment.core.application.services.ValidationService;
import com.epayment.core.application.repositories.AccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListAccountDetailsController {
  private AccountRepository accountRepository;
  private ValidationService validationService;

  public ListAccountDetailsController(
    AccountRepository accountRepository,
    ValidationService validationService
  ) {
    this.accountRepository = accountRepository;
    this.validationService = validationService;
  }

  @GetMapping("/account")
  @ResponseBody
  public AccountDetails handle(@RequestBody Request request) {
    this.validationService.execute(request);
    var accountQuery = accountRepository.findByEmail(request.email);

    if (accountQuery.isEmpty()) {
      throw new OperationalException("account does not exist");
    }

    var account = accountQuery.get();
    return AccountDetails.of(account);
  }

  public static record Request(@NotNull @Email String email) {}
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

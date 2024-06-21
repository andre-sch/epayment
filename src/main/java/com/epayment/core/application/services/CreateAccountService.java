package com.epayment.core.application.services;

import java.math.BigDecimal;
import com.epayment.core.domain.Account;
import com.epayment.core.application.repositories.AccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CreateAccountService {
  private final BigDecimal initialCredit = BigDecimal.valueOf(10000L, 2);
  
  private AccountRepository accountRepository;
  private PasswordEncoder passwordEncoder;

  public CreateAccountService(
    AccountRepository accountRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Account execute(CreateAccountService.Request request) {
    boolean emailAlreadyInUse = this.accountRepository.findByEmail(request.email).isPresent();
    if (emailAlreadyInUse) throw new RuntimeException("email already in use");

    var account = new Account();

    account.setFullName(request.fullName);
    account.setEmail(request.email);
    
    String passwordHash = this.passwordEncoder.encode(request.password);
    account.setPassword(passwordHash);
    
    account.credit(initialCredit);
    
    this.accountRepository.save(account);

    return account;
  }

  public static record Request(
    String fullName,
    String email,
    String password
  ) {}
}

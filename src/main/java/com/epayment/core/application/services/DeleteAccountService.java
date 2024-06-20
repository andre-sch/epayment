package com.epayment.core.application.services;

import com.epayment.core.application.repositories.UserRepository;
import com.epayment.core.application.repositories.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccountService {
  private UserRepository userRepository;
  private WalletRepository walletRepository;
  
  public DeleteAccountService(
    UserRepository userRepository,
    WalletRepository walletRepository
  ) {
    this.userRepository = userRepository;
    this.walletRepository = walletRepository;
  }

  public void execute(String email) {
    var userQuery = this.userRepository.findByEmail(email);

    if (userQuery.isEmpty()) {
      throw new RuntimeException("user does not exist");
    }

    var user = userQuery.get();

    this.walletRepository
      .findByOwner(user)
      .forEach(this.walletRepository::delete);

    this.userRepository.delete(user);
  }
}

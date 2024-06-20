package com.epayment.core.application.services;

import java.math.BigDecimal;
import com.epayment.core.domain.Wallet;
import com.epayment.core.application.repositories.UserRepository;
import com.epayment.core.application.repositories.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletService {
  private final BigDecimal initialCredit = BigDecimal.valueOf(10000L, 2);

  private WalletRepository walletRepository;
  private UserRepository userRepository;
  
  public CreateWalletService(
    WalletRepository walletRepository,
    UserRepository userRepository
  ) {
    this.walletRepository = walletRepository;
    this.userRepository = userRepository;
  }

  public Wallet execute(int ownerId) {
    var ownerQuery = this.userRepository.findById(ownerId);

    if (ownerQuery.isEmpty()) {
      throw new RuntimeException("owner does not exist");
    }

    var owner = ownerQuery.get();
    var wallet = new Wallet();

    wallet.setOwner(owner);
    wallet.credit(initialCredit);

    this.walletRepository.save(wallet);

    return wallet;
  }
}

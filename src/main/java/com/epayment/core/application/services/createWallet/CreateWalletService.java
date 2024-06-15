package com.epayment.core.application.services.createWallet;

import com.epayment.core.domain.Wallet;
import com.epayment.core.application.repositories.UserRepository;
import com.epayment.core.application.repositories.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletService {
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
    var ownerSearch = this.userRepository.findById(ownerId);

    if (ownerSearch.isEmpty()) {
      throw new RuntimeException("owner does not exist");
    }

    var owner = ownerSearch.get();
    var wallet = new Wallet();

    wallet.setOwner(owner);

    this.walletRepository.save(wallet);

    return wallet;
  }
}

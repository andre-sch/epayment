package com.epayment.core.services.createWallet;

import com.epayment.core.entities.Wallet;
import com.epayment.core.repositories.UserRepository;
import com.epayment.core.repositories.WalletRepository;
import com.epayment.core.exceptions.OwnerAbsentException;
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
      throw new OwnerAbsentException("owner does not exist");
    }

    var owner = ownerSearch.get();
    var wallet = new Wallet();

    wallet.setOwner(owner);

    this.walletRepository.save(wallet);

    return wallet;
  }
}

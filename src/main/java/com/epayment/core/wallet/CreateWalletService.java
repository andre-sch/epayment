package com.epayment.core.wallet;

import com.epayment.core.user.UserRepository;
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

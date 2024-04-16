package com.moneytransactions.core.wallet;

import com.moneytransactions.core.user.UserRepository;
import com.moneytransactions.core.exception.RequestException;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
  private WalletRepository walletRepository;
  private UserRepository userRepository;
  
  public WalletService(
    WalletRepository walletRepository,
    UserRepository userRepository
  ) {
    this.walletRepository = walletRepository;
    this.userRepository = userRepository;
  }

  public Wallet create(int ownerId) {
    var ownerSearch = this.userRepository.findById(ownerId);

    if (ownerSearch.isEmpty()) {
      throw new RequestException("owner does not exist");
    }

    var owner = ownerSearch.get();
    var wallet = new Wallet();

    wallet.setOwner(owner);

    this.walletRepository.save(wallet);

    return wallet;
  }
}

package com.epayment.core.application.repositories;

import java.util.List;
import com.epayment.core.domain.User;
import com.epayment.core.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
  public List<Wallet> findByOwner(User owner);
}

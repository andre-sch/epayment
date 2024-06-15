package com.epayment.core.application.repositories;

import com.epayment.core.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {}

package com.epayment.core.application.repositories;

import java.util.Optional;
import com.epayment.core.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
  Optional<Account> findByEmail(String email);
}

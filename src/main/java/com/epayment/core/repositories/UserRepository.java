package com.epayment.core.repositories;

import java.util.Optional;
import com.epayment.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
  Optional<User> findByDocument(String document);
}

package com.epayment.core.application.repositories;

import com.epayment.core.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {}

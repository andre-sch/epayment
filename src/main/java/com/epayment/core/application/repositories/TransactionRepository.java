package com.epayment.core.application.repositories;

import java.util.List;
import com.epayment.core.domain.Account;
import com.epayment.core.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
  @Query("select t from Transaction t where t.sender = :account or t.receiver = :account")
  List<Transaction> findByAccount(Account account);
}

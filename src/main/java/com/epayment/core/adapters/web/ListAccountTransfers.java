package com.epayment.core.adapters.web;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import com.epayment.core.domain.Account;
import com.epayment.core.domain.Transaction;
import com.epayment.core.domain.exceptions.OperationalException;
import com.epayment.core.application.repositories.TransactionRepository;
import com.epayment.core.application.repositories.AccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListAccountTransfers {
  private AccountRepository accountRepository;
  private TransactionRepository transactionRepository;

  public ListAccountTransfers(
    AccountRepository accountRepository,
    TransactionRepository transactionRepository
  ) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @GetMapping("/account/transfers")
  @ResponseBody
  public List<TransactionView> handle(@RequestBody Request request) {
    var accountQuery = this.accountRepository.findByEmail(request.email);

    if (accountQuery.isEmpty()) {
      throw new OperationalException("account does not exist");
    }

    var account = accountQuery.get();

    return this.transactionRepository
      .findByAccount(account)
      .stream().map(TransactionView::of).toList();
  }

  public static record Request(String email) {}
}

record TransactionView(
  BigDecimal amount,
  Endpoint sender,
  Endpoint receiver,
  Instant completedAt
) {
  public static TransactionView of(Transaction transaction) {
    return new TransactionView(
      transaction.getAmount(),
      Endpoint.of(transaction.getSender()),
      Endpoint.of(transaction.getReceiver()),
      transaction.getCompletedAt()
    );
  }

  public static record Endpoint(String email, String fullName) {
    public static Endpoint of(Account account) {
      return new Endpoint(account.getEmail(), account.getFullName());
    }
  }
}

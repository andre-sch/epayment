package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.application.repositories.*;
import com.epayment.core.application.services.*;
import com.epayment.core.utils.DummyAccountFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TransferResourceServiceTests {
  private final BigDecimal zero = BigDecimal.valueOf(0L, 2);
  private final BigDecimal amount = BigDecimal.valueOf(1L, 2);
  private final DummyAccountFactory accountFactory = new DummyAccountFactory();
  private final TransactionParser transactionParser = new TransactionParser();

  @MockBean private EventDispatcher<BalanceChanged> balanceChangedDispatcher;
  @MockBean private EventDispatcher<TransactionFailed> transactionFailedDispatcher;
  @Autowired private AccountRepository accountRepository;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private TransferResourceService transferResourceService;

  @BeforeEach
  public void resetRepositories() {
    this.transactionRepository.deleteAll();
    this.accountRepository.deleteAll();
  }

  @Test
  public void resourceTransferenceSucceeds() {
    var receiver = this.insertedAccount();
    var sender = this.insertedAccount();

    var request = new TransferResourceService.Request(sender.getEmail(), receiver.getEmail(), amount);
    var transaction = this.transferResourceService.execute(request);

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(transaction.getReceiver().getBalance()).isEqualTo(amount.add(amount));
    assertThat(transaction.getSender().getBalance()).isEqualTo(zero);
    transactionParser
      .parseBalanceChangesOf(transaction)
      .forEach((var change) -> verify(balanceChangedDispatcher).dispatch(change));
    
    this.transactionRepository.deleteAll();
  }

  @Test
  public void resourceTransferenceFails() {
    var request = new TransferResourceService.Request("unknown.sender@email", "unknown.receiver@email", amount);
    
    assertThrows(RuntimeException.class, () -> {
      this.transferResourceService.execute(request);
    });
    
    var unknownEndpoint = TransactionFailed.Endpoint.of(null);
    verify(transactionFailedDispatcher).dispatch(
      new TransactionFailed(
        amount,
        unknownEndpoint,
        unknownEndpoint
      )
    );
  }

  private Account insertedAccount() {
    var account = accountFactory.build();
    this.accountRepository.save(account);
    return account;
  }
}

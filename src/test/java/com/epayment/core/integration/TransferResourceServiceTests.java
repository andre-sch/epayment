package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.application.repositories.*;
import com.epayment.core.application.services.transferResource.*;
import com.epayment.core.utils.DummyWalletFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TransferResourceServiceTests {
  private final BigDecimal zero = BigDecimal.valueOf(0L, 2);
  private final BigDecimal amount = BigDecimal.valueOf(1L, 2);
  private final DummyWalletFactory walletFactory = new DummyWalletFactory();
  private final TransactionParser transactionParser = new TransactionParser();

  @MockBean private EventDispatcher<BalanceChanged> eventDispatcher;
  @Autowired private UserRepository userRepository;
  @Autowired private WalletRepository walletRepository;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private TransferResourceService transferResourceService;

  @BeforeEach
  public void resetRepositories() {
    this.transactionRepository.deleteAll();
    this.walletRepository.deleteAll();
    this.userRepository.deleteAll();
  }

  @Test
  public void resourceTransferenceSucceeds() {
    var receiver = this.insertedWallet();
    var sender = this.insertedWallet();

    var request = new ResourceTransferenceRequest(sender.getId(), receiver.getId(), amount);
    var transaction = this.transferResourceService.execute(request);

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(transaction.getReceiver().getBalance()).isEqualTo(amount.add(amount));
    assertThat(transaction.getSender().getBalance()).isEqualTo(zero);
    transactionParser
      .parseBalanceChangesOf(transaction)
      .forEach((var change) -> verify(eventDispatcher).dispatch(change));
    
    this.transactionRepository.deleteAll();
  }

  private Wallet insertedWallet() {
    var wallet = walletFactory.build();

    this.userRepository.save(wallet.getOwner());
    this.walletRepository.save(wallet);

    return wallet;
  }
}

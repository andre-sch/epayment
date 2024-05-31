package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.*;
import com.epayment.core.repositories.*;
import com.epayment.core.services.transferResource.*;
import com.epayment.core.utils.DummyWalletFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TransferResourceServiceTests {
  private final BigDecimal zero = BigDecimal.valueOf(0L, 2);
  private final BigDecimal amount = BigDecimal.valueOf(1L, 2);
  private final DummyWalletFactory walletFactory = new DummyWalletFactory();

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
    this.transactionRepository.deleteAll();
  }

  private Wallet insertedWallet() {
    var wallet = walletFactory.build();

    this.userRepository.save(wallet.getOwner());
    this.walletRepository.save(wallet);

    return wallet;
  }
}

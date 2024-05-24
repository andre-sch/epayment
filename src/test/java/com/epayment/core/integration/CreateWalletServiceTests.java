package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.DummyUser;
import com.epayment.core.repositories.*;
import com.epayment.core.services.createWallet.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreateWalletServiceTests {
  @Autowired private UserRepository userRepository;
  @Autowired private WalletRepository walletRepository;
  @Autowired private CreateWalletService createWalletService;

  @BeforeEach
  public void resetRepositories() {
    this.walletRepository.deleteAll();
    this.userRepository.deleteAll();
  }

  @Test
  public void walletCreationSucceeds() {
    var owner = DummyUser.get();
    this.userRepository.save(owner);

    var wallet = this.createWalletService.execute(owner.getId());

    assertThat(wallet.getOwner()).isEqualTo(owner);
    assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ZERO);
  }
}

package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.utils.*;
import com.epayment.core.application.repositories.*;
import com.epayment.core.application.services.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreateWalletServiceTests {
  private final DummyUserFactory userFactory = new DummyUserFactory();

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
    var owner = userFactory.build();
    this.userRepository.save(owner);

    var wallet = this.createWalletService.execute(owner.getId());

    assertThat(wallet.getOwner()).isEqualTo(owner);
    assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ZERO);
  }
}

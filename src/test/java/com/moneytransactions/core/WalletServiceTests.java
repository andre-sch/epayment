package com.moneytransactions.core;

import java.util.Optional;
import java.math.BigDecimal;
import com.moneytransactions.core.user.*;
import com.moneytransactions.core.wallet.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WalletServiceTests {
  private final int ownerId = 0;

  @MockBean private UserRepository userRepository;
  @MockBean private WalletRepository walletRepository;
  @InjectMocks private CreateWalletService createWalletService;

  @Test
  public void walletCreationSucceeds() {
    var owner = new User(ownerId);
    when(this.userRepository.findById(ownerId))
      .thenReturn(Optional.of(owner));

    var wallet = this.createWalletService.execute(ownerId);

    assertThat(wallet.getOwner()).isEqualTo(owner);
    assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void walletCreationFailsWithOwnerAbsent() {
    assertThrows(OwnerAbsentException.class, () -> {
      this.createWalletService.execute(ownerId);
    });
  }
}

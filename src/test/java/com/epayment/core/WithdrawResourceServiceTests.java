package com.epayment.core;

import java.util.Optional;
import java.math.BigDecimal;
import com.epayment.core.entities.*;
import com.epayment.core.repositories.*;
import com.epayment.core.services.withdrawResource.*;
import com.epayment.core.exceptions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WithdrawResourceServiceTests {
  private final int withdrawerId = 0;
  private final BigDecimal amount = BigDecimal.TEN;

  @MockBean private CashMachine cashMachine;
  @MockBean private WalletRepository walletRepository;
  @MockBean private TransactionRepository transactionRepository;
  @InjectMocks private WithdrawResourceService withdrawResourceService;

  @Test
  public void resourceWithdrawalSucceeds() {
    var withdrawer = this.stubWallet(withdrawerId);
    withdrawer.credit(amount);

    var request = new ResourceWithdrawalRequest(withdrawerId, amount);
    var transaction = this.withdrawResourceService.execute(request);

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(withdrawer.getBalance()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void operationFailsWithWithdrawerAbsent() {
    var request = new ResourceWithdrawalRequest(withdrawerId, amount);
    
    assertThrows(TransactionEndpointAbsentException.class, () -> {
      this.withdrawResourceService.execute(request);
    });
  }

  private Wallet stubWallet(int id) {
    var wallet = new Wallet(id);
    
    when(this.walletRepository.findById(id))
      .thenReturn(Optional.of(wallet));
    
    return wallet;
  }
}

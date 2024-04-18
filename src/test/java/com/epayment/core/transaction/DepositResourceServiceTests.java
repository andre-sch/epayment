package com.epayment.core.transaction;

import java.util.Optional;
import java.math.BigDecimal;
import com.epayment.core.CashMachine;
import com.epayment.core.entities.*;
import com.epayment.core.repositories.*;
import com.epayment.core.services.depositResource.*;
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
public class DepositResourceServiceTests {
  private final int depositorId = 0;
  private final BigDecimal amount = BigDecimal.TEN;

  @MockBean private CashMachine cashMachine;
  @MockBean private WalletRepository walletRepository;
  @MockBean private TransactionRepository transactionRepository;
  @InjectMocks private DepositResourceService depositResourceService;

  @Test
  public void resourceDepositSucceeds() {
    var depositor = this.stubWallet(depositorId);
    when(cashMachine.readDepositedAmount()).thenReturn(amount);

    var transaction = this.depositResourceService.execute(depositorId);

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(depositor.getBalance()).isEqualTo(amount);
  }

  @Test
  public void operationFailsWithDepositorAbsent() {
    assertThrows(TransactionEndpointAbsentException.class, () -> {
      this.depositResourceService.execute(depositorId);
    });
  }

  private Wallet stubWallet(int id) {
    var wallet = new Wallet(id);
    
    when(this.walletRepository.findById(id))
      .thenReturn(Optional.of(wallet));
    
    return wallet;
  }
}

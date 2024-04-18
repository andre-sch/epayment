package com.epayment.core.transaction;

import java.util.Optional;
import java.math.BigDecimal;
import com.epayment.core.entities.*;
import com.epayment.core.repositories.*;
import com.epayment.core.services.transferResource.*;
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
public class TransferResourceServiceTests {
  private final int senderId = 0;
  private final int receiverId = 1;
  private final BigDecimal amount = BigDecimal.TEN;

  @MockBean private WalletRepository walletRepository;
  @MockBean private TransactionRepository transactionRepository;
  @InjectMocks private TransferResourceService transferResourceService;

  @Test
  public void resourceTransferenceSucceeds() {
    var receiver = this.stubWallet(receiverId);
    var sender = this.stubWallet(senderId);
    sender.credit(amount);

    var request = new ResourceTransferenceRequest(senderId, receiverId, amount);
    var transaction = this.transferResourceService.execute(request);

    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(receiver.getBalance()).isEqualTo(amount);
    assertThat(sender.getBalance()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void resourceTransferenceFailsWithEndpointAbsent() {
    var sender = this.stubWallet(senderId);
    sender.credit(amount);

    var request = new ResourceTransferenceRequest(senderId, receiverId, amount);
    
    assertThrows(TransactionEndpointAbsentException.class, () -> {
      this.transferResourceService.execute(request);
    });
  }

  @Test
  public void resourceTransferenceFailsWithInsufficientFunds() {
    this.stubWallet(receiverId);
    var sender = this.stubWallet(senderId);
    sender.credit(amount);
    
    var greaterAmount = amount.add(BigDecimal.ONE);
    var request = new ResourceTransferenceRequest(senderId, receiverId, greaterAmount);

    assertThrows(InsufficientFundsException.class, () -> {
      this.transferResourceService.execute(request);
    });
  }

  private Wallet stubWallet(int id) {
    var wallet = new Wallet(id);
    
    when(this.walletRepository.findById(id))
      .thenReturn(Optional.of(wallet));
    
    return wallet;
  }
}

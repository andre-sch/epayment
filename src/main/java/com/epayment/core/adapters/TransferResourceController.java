package com.epayment.core.adapters;

import com.epayment.core.application.services.transferResource.*;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.web.bind.annotation.*;

@RestController
public class TransferResourceController {
  private TransferResourceService transferResourceService;

  public TransferResourceController(
    TransferResourceService transferResourceService
  ) {
    this.transferResourceService = transferResourceService;
  }

  @PostMapping("/transfer")
  @ResponseBody
  public TransferenceView handle(@RequestBody ResourceTransferenceRequest request) {
    var transaction = this.transferResourceService.execute(request);
    return new TransferenceView(
      transaction.getSender().getOwner().getEmail(),
      transaction.getReceiver().getOwner().getEmail(),
      transaction.getAmount(),
      transaction.getCompletedAt()
    );
  }
}

record TransferenceView(
  String sender,
  String receiver,
  BigDecimal amount,
  Instant timestamp
) {}

package com.epayment.core.adapters.web;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.application.services.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferResourceController {
  private TransferResourceService transferResourceService;
  private ValidationService validationService;

  public TransferResourceController(
    TransferResourceService transferResourceService,
    ValidationService validationService
  ) {
    this.transferResourceService = transferResourceService;
    this.validationService = validationService;
  }

  @PostMapping("/transfer")
  @ResponseBody
  public TransferenceView handle(@RequestBody TransferResourceService.Request request) {
    this.validationService.execute(request);
    var transaction = this.transferResourceService.execute(request);
    
    return new TransferenceView(
      transaction.getSender().getEmail(),
      transaction.getReceiver().getEmail(),
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

package com.epayment.core.services.transferResource;

import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class ResourceTransferenceRequest {
  public int senderId;
  public int receiverId;
  public BigDecimal amount;
}

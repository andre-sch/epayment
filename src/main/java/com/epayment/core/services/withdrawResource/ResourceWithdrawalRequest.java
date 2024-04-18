package com.epayment.core.services.withdrawResource;

import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class ResourceWithdrawalRequest {
  public int withdrawerId;
  public BigDecimal amount;
}

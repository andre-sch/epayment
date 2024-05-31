package com.epayment.core.utils;

import java.math.BigDecimal;
import com.epayment.core.domain.Wallet;

public class DummyWalletFactory extends DummyFactory<Wallet> {
  private final BigDecimal amount = BigDecimal.valueOf(1L, 2);
  private final DummyUserFactory userFactory = new DummyUserFactory();

  public Wallet build() {
    var wallet = new Wallet();

    wallet.setOwner(userFactory.build());
    wallet.credit(amount);

    return wallet;
  }
}

package com.epayment.core.adapters.kafka;

import java.math.BigDecimal;
import com.epayment.core.domain.AccountCreated;
import com.epayment.core.domain.AccountDeleted;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventKafkaLogger extends EventLogger {
  public AccountEventKafkaLogger() {
    super(new DataFilter[] {
      new AccountCreatedDataFilter(),
      new AccountDeletedDataFilter()
    });
  }

  @KafkaListener(topics = "accounts", groupId = "accounts_logger")
  public void log(String serializedEvent) {
    super.log(serializedEvent);
  }
}

class AccountCreatedDataFilter implements EventLogger.DataFilter {
  public Class<?> getInputClass() {
    return AccountCreated.class;
  }
  
  public AccountCreationFiltered filter(Object input) {
    var event = (AccountCreated) input;
    return new AccountCreationFiltered(event.id(), event.balance());
  }
  
  public static record AccountCreationFiltered(int id, BigDecimal balance) {}
}

class AccountDeletedDataFilter implements EventLogger.DataFilter {
  public Class<?> getInputClass() {
    return AccountDeleted.class;
  }
  
  public AccountDeletionFiltered filter(Object input) {
    var event = (AccountDeleted) input;
    return new AccountDeletionFiltered(event.id(), event.oldBalance());
  }

  public static record AccountDeletionFiltered(int id, BigDecimal oldBalance) {}
}

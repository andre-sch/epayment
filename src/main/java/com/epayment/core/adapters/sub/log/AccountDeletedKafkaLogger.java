package com.epayment.core.adapters.sub.log;

import java.math.BigDecimal;
import com.epayment.core.domain.AccountDeleted;
import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDeletedKafkaLogger extends EventLogger<AccountDeleted> {
@Autowired private JsonConverter json;

  @KafkaListener(topics = "accounts", groupId = "accounts_deletion_logger")
  public void log(String serializedEvent) {
    if (json.match(serializedEvent, AccountDeleted.class)) {
      var event = json.deserialize(serializedEvent, AccountDeleted.class);
      super.log(event);
    }
  }

  public AccountDeletionFiltered filterSensitiveData(AccountDeleted event) {
    return new AccountDeletionFiltered(event.id(), event.oldBalance());
  }

  public static record AccountDeletionFiltered(int id, BigDecimal oldBalance) {}
}

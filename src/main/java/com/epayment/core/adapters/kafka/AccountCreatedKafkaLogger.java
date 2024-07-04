package com.epayment.core.adapters.kafka;

import java.math.BigDecimal;
import com.epayment.core.domain.AccountCreated;
import com.epayment.core.application.interfaces.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreatedKafkaLogger extends EventLogger<AccountCreated> {
  @Autowired private JsonConverter json;

  @KafkaListener(topics = "accounts", groupId = "accounts_creation_logger")
  public void log(String serializedEvent) {
    if (json.match(serializedEvent, AccountCreated.class)) {
      var event = json.deserialize(serializedEvent, AccountCreated.class);
      super.log(event);
    }
  }
  
  public AccountCreationFiltered filterSensitiveData(AccountCreated event) {
    return new AccountCreationFiltered(event.id(), event.balance());
  }
  
  public static record AccountCreationFiltered(int id, BigDecimal balance) {}
}

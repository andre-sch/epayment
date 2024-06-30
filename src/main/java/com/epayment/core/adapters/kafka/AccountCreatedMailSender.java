package com.epayment.core.adapters.kafka;

import java.text.MessageFormat;
import com.epayment.core.domain.AccountCreated;
import com.epayment.core.application.interfaces.JsonConverter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class AccountCreatedMailSender extends MailSender<AccountCreated> {
  @Autowired private JsonConverter json;

  public AccountCreatedMailSender() {
    super(
      "account creation",
      "account",
      true
    );
  }

  @KafkaListener(topics = "accounts", groupId = "accounts_mail")
  public void send(String serializedEvent) {
    if (json.match(serializedEvent, AccountCreated.class)) {
      var event = json.deserialize(serializedEvent, AccountCreated.class);
      super.send(event);
    }
  }

  protected String clientOf(AccountCreated event) { return event.fullName(); }
  protected String recipientOf(AccountCreated event) { return event.email(); }

  protected String subjectOf(AccountCreated event) {
    return "Your Epayment account has been created successfully";
  }

  protected String contentOf(AccountCreated event) {
    return MessageFormat.format(
      """
      Balance: {0}$
      Full name: {1}
      Email: {2}
      """,
      event.balance(),
      event.fullName(),
      event.email()
    );
  }
}

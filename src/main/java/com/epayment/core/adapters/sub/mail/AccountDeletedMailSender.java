package com.epayment.core.adapters.sub.mail;

import com.epayment.core.domain.events.AccountDeleted;
import com.epayment.core.application.interfaces.JsonConverter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class AccountDeletedMailSender extends MailSender<AccountDeleted> {
  @Autowired private JsonConverter json;

  public AccountDeletedMailSender() {
    super(
      "account deletion",
      "account",
      true
    );
  }

  @KafkaListener(topics = "accounts", groupId = "deleted_accounts_mail")
  public void send(String serializedEvent) {
    if (json.match(serializedEvent, AccountDeleted.class)) {
      var event = json.deserialize(serializedEvent, AccountDeleted.class);
      super.send(event);
    }
  }

  protected String clientOf(AccountDeleted event) { return event.fullName(); }
  protected String recipientOf(AccountDeleted event) { return event.email(); }

  protected String subjectOf(AccountDeleted event) {
    return "Your Epayment account has been deleted";
  }

  protected String textOf(AccountDeleted event) {
    return String.join(
      delimiter,
      headerOf(event),
      footer()
    );
  }

  protected String headerOf(AccountDeleted event) {
    return String.join(
      delimiter,
      greetings(clientOf(event)),
      blankLine,
      isSuccessful
        ? summaryOfSuccess()
        : summaryOfFailure()
    );
  }

  protected String contentOf(AccountDeleted event) { return null; }
}

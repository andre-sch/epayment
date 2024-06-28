package com.epayment.core.adapters.kafka;

import java.util.*;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.application.interfaces.JsonConverter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class BalanceChangedMailSender extends MailSender<BalanceChanged> {
  @Autowired private JsonConverter json;

  public BalanceChangedMailSender() {
    super(
      "balance change",
      "transaction",
      true
    );
  }

  @KafkaListener(topics = "balances", groupId = "balances_mail")
  public void send(String serializedEvent) {
    var event = json.deserialize(serializedEvent, BalanceChanged.class);
    super.send(event);
  }

  protected String clientOf(BalanceChanged event) { return event.client().fullName(); }
  protected String recipientOf(BalanceChanged event) { return event.client().email(); }

  protected String subjectOf(BalanceChanged event) {
    String partner = event.partner().fullName();
    BigDecimal delta = event.delta();
    BigDecimal amount = delta.abs();

    String status = "Transaction completed";
    String template = delta.compareTo(BigDecimal.ZERO) > 0
      ? "received {0}$ from {1}"
      : "sent {0}$ to {1}";
    
    String delimiter = ": ";
    return status + delimiter + MessageFormat.format(template, amount, partner);
  }

  protected String contentOf(BalanceChanged event) {
    String partner = event.partner().fullName();
    BigDecimal delta = event.delta();
    String time = formatInstant(event.timestamp());

    return MessageFormat.format(
      """
      Partner: {0}
      Balance Change: {1}$
      Date and time: {2}
      """,
      partner,
      delta,
      time
    );
  }

  private String formatInstant(Instant time) {
    var dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss 'GMT'");
    dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormatter.format(Date.from(time));
  }
}

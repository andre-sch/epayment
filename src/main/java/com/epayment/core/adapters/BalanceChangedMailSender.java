package com.epayment.core.adapters;

import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.application.interfaces.JsonConverter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Component
public class BalanceChangedMailSender {
  @Autowired private JsonConverter json;
  @Autowired private JavaMailSender mailSender;
  
  @KafkaListener(topics = "balances", groupId = "mail")
  public void sendMail(String serializedEvent) {
    var event = json.deserialize(serializedEvent, BalanceChanged.class);

    var message = new SimpleMailMessage();

    message.setTo(event.client().email());
    message.setSubject(getSubjectOf(event));
    message.setText(getContentOf(event));

    mailSender.send(message);
  }

  private String getSubjectOf(BalanceChanged event) {
    String partner = event.partner().fullName();
    BigDecimal delta = event.delta();
    BigDecimal amount = delta.abs();

    String status = "Transaction completed";
    String template = delta.compareTo(BigDecimal.ZERO) > 0
    ? "received %s$ from %s"
    : "sent %s$ to %s";
    
    String delimiter = ": ";
    return status + delimiter + template.formatted(amount, partner);
  }

  private String getContentOf(BalanceChanged event) {
    String client = event.client().fullName();
    String partner = event.partner().fullName();
    BigDecimal delta = event.delta();
    
    var dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss 'GMT'");
    dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    String timestamp = dateFormatter.format(Date.from(event.timestamp()));

    return
      """
      Dear %s,

      Your transaction has been successfully processed. See details:

      Partner: %s
      Balance Change: %s$
      Date and time: %s

      Thank you for trust in our services.

      Best regards,
      Epayment
      """.formatted(client, partner, delta, timestamp);
  }
}

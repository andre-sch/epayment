package com.epayment.core.adapters;

import java.util.Date;
import java.math.BigDecimal;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.application.interfaces.JsonConverter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Component
public class BalanceChangedMailSender {
  @Autowired JsonConverter json;
  @Autowired JavaMailSender mailSender;

  @KafkaListener(topics = "balances", groupId = "mail")
  public void sendMail(String serializedEvent) {
    var event = json.deserialize(serializedEvent, BalanceChanged.class);

    var message = new SimpleMailMessage();

    message.setTo(event.client().email());
    message.setSubject(formatSubject(event));
    message.setText(formatText(event));

    mailSender.send(message);
  }

  private String formatSubject(BalanceChanged event) {
    return formatSubject(
      event.partner().fullName(),
      event.delta()
    );
  }

  private String formatSubject(
    String partnerName,
    BigDecimal delta
  ) {
    String status = "Transaction completed";
    String template = delta.compareTo(BigDecimal.ZERO) > 0
    ? "received %s$ from %s"
    : "sent %s$ to %s";
    
    String delimiter = ": ";
    return status + delimiter + template.formatted(delta, partnerName);
  }

  private String formatText(BalanceChanged event) {
    return formatText(
      event.client().fullName(),
      event.partner().fullName(),
      event.delta(),
      Date.from(event.timestamp())
    );
  }

  private String formatText(
    String clientName,
    String partnerName,
    BigDecimal delta,
    Date date
  ) {
    return """
      Dear %s,

      Your transaction has been successfully processed. See details:

      Partner: %s
      Balance Change: %s$
      Date and time: %s

      Thank you for trust in our services.

      Best regards,
      Epayment
    """.formatted(clientName, partnerName, delta, date);
  }
}

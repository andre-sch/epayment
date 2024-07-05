package com.epayment.core.adapters.sub.mail;

import java.text.MessageFormat;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public abstract class MailSender<Event> {
  protected final String blankLine = "";
  protected final String delimiter = "\n";
  
  protected final String eventName;
  protected final String entityName;
  protected final boolean isSuccessful;
  
  @Autowired private JavaMailSender mailSender;

  public MailSender(
    String eventName,
    String entityName,
    boolean isSuccessful
  ) {
    this.eventName = eventName;
    this.entityName = entityName;
    this.isSuccessful = isSuccessful;
  }

  public void send(Event event) {
    var message = new SimpleMailMessage();

    message.setTo(recipientOf(event));
    message.setSubject(subjectOf(event));
    message.setText(textOf(event));

    mailSender.send(message);
  }

  protected abstract String clientOf(Event event);
  protected abstract String recipientOf(Event event);
  protected abstract String subjectOf(Event event);

  protected String textOf(Event event) {
    return String.join(
      delimiter,
      headerOf(event),
      contentOf(event),
      footer()
    );
  }

  protected String headerOf(Event event) {
    return String.join(
      delimiter,
      greetings(clientOf(event)),
      blankLine,
      isSuccessful
        ? summaryOfSuccess()
        : summaryOfFailure(),
      contentIntroduction(),
      blankLine
    );
  }

  protected String greetings(String clientName) {
    return MessageFormat.format("Dear {0},", clientName);
  }

  protected String summaryOfSuccess() {
    return MessageFormat.format("Your {0} has been successfully processed.", eventName);
  }

  protected String summaryOfFailure() {
    return MessageFormat.format("Your {0} could not be processed.", eventName);
  }

  protected String contentIntroduction() {
    return MessageFormat.format("Below are the details of your {0}:", entityName);
  }

  protected abstract String contentOf(Event event);

  protected String footer() {
    return
      """
      Thank you for trust in our services.
  
      Best regards,
      Epayment
      """;
  }
}

package com.epayment.core.integration;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.adapters.kafka.BalanceChangedMailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class BalanceChangedMailSenderTests {
  @Autowired private JavaMailSender javaMailSender;
  @Autowired private BalanceChangedMailSender eventMailSender;

  @Test
  public void mailSending() {
    // arrange
    var event = new BalanceChanged(
      BigDecimal.valueOf(1001L, 2),
      new BalanceChanged.Endpoint(1, "client@email", "clientName"),
      new BalanceChanged.Endpoint(2, "partner@email", "partnerName"),
      Instant.ofEpochMilli(0L)
    );

    // act
    eventMailSender.send(event);

    // assert
    var expectedMessage = new SimpleMailMessage();

    expectedMessage.setTo("client@email");
    expectedMessage.setSubject("Transaction completed: received 10.01$ from partnerName");
    expectedMessage.setText(
      """
      Dear clientName,

      Your balance change has been successfully processed.
      Below are the details of your transaction:

      Partner: partnerName
      Balance Change: 10.01$
      Date and time: 01-01-1970 00:00:00 GMT

      Thank you for trust in our services.

      Best regards,
      Epayment
      """
    );
    
    verify(javaMailSender).send(expectedMessage);
  }
}

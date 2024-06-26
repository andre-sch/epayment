package com.epayment.core.integration;

import java.time.Instant;
import java.math.BigDecimal;
import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.application.interfaces.JsonConverter;
import com.epayment.core.adapters.kafka.BalanceChangedMailSender;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class BalanceChangedMailSenderTests {
  @MockBean private JavaMailSender javaMailSender;
  @Autowired private BalanceChangedMailSender eventMailSender;
  @Autowired private JsonConverter json; 

  @Test
  public void mailSending() {
    // arrange
    String serializedEvent = json.serialize(
      new BalanceChanged(
        BigDecimal.valueOf(100L, 2),
        new BalanceChanged.Endpoint("client@email", "clientName"),
        new BalanceChanged.Endpoint("partner@email", "partnerName"),
        Instant.ofEpochMilli(0L)
      )
    );

    // act
    eventMailSender.sendMail(serializedEvent);

    // assert
    var expectedMessage = new SimpleMailMessage();

    expectedMessage.setTo("client@email");
    expectedMessage.setSubject("Transaction completed: received 1.00$ from partnerName");
    expectedMessage.setText(
      """
      Dear clientName,

      Your transaction has been successfully processed. See details:

      Partner: partnerName
      Balance Change: 1.00$
      Date and time: 01-01-1970 00:00:00 GMT

      Thank you for trust in our services.

      Best regards,
      Epayment
      """
    );
    
    verify(javaMailSender).send(expectedMessage);
  }
}

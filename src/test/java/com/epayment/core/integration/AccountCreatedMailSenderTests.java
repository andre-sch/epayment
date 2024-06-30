package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.AccountCreated;
import com.epayment.core.application.interfaces.JsonConverter;
import com.epayment.core.adapters.kafka.AccountCreatedMailSender;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class AccountCreatedMailSenderTests {
  @Autowired private JavaMailSender javaMailSender;
  @Autowired private AccountCreatedMailSender eventMailSender;
  @Autowired private JsonConverter json; 

  @Test
  public void mailSending() {
    // arrange
    String serializedEvent = json.serialize(
      new AccountCreated(
        1,
        "client@email",
        "clientName",
        BigDecimal.valueOf(1001L, 2)
      )
    );

    // act
    eventMailSender.send(serializedEvent);

    // assert
    var expectedMessage = new SimpleMailMessage();

    expectedMessage.setTo("client@email");
    expectedMessage.setSubject("Your Epayment account has been created successfully");
    expectedMessage.setText(
      """
      Dear clientName,

      Your account creation has been successfully processed.
      Below are the details of your account:

      Balance: 10.01$
      Full name: clientName
      Email: client@email

      Thank you for trust in our services.

      Best regards,
      Epayment
      """
    );
    
    verify(javaMailSender).send(expectedMessage);
  }
}

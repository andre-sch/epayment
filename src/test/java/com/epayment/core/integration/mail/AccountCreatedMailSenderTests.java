package com.epayment.core.integration.mail;

import java.math.BigDecimal;
import com.epayment.core.domain.events.AccountCreated;
import com.epayment.core.adapters.sub.mail.AccountCreatedMailSender;
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

  @Test
  public void mailSending() {
    // arrange
    int accountId = 1;
    var event = new AccountCreated(
      accountId,
      "client@email",
      "clientName",
      BigDecimal.valueOf(1001L, 2)
    );

    // act
    eventMailSender.send(event);

    // assert
    var expectedMessage = new SimpleMailMessage();

    expectedMessage.setTo("client@email");
    expectedMessage.setSubject("Your Epayment account has been created successfully");
    expectedMessage.setText(
      """
      Dear clientName,

      Your account creation has been successfully processed.
      Below are the details of your account:

      Initial credit: 10.01$
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

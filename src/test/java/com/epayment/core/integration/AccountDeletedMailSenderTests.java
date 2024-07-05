package com.epayment.core.integration;

import java.math.BigDecimal;
import com.epayment.core.domain.AccountDeleted;
import com.epayment.core.adapters.sub.mail.AccountDeletedMailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class AccountDeletedMailSenderTests {
  @Autowired private JavaMailSender javaMailSender;
  @Autowired private AccountDeletedMailSender eventMailSender;

  @Test
  public void mailSending() {
    // arrange
    int accountId = 1;
    var event = new AccountDeleted(
      accountId,
      "client@email",
      "clientName",
      BigDecimal.ONE
    );

    // act
    eventMailSender.send(event);

    // assert
    var expectedMessage = new SimpleMailMessage();

    expectedMessage.setTo("client@email");
    expectedMessage.setSubject("Your Epayment account has been deleted");
    expectedMessage.setText(
      """
      Dear clientName,

      Your account deletion has been successfully processed.
      Thank you for trust in our services.

      Best regards,
      Epayment
      """
    );
    
    verify(javaMailSender).send(expectedMessage);
  }
}

package com.epayment.core.providers;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderProvider {
  public @Bean PasswordEncoder provideBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

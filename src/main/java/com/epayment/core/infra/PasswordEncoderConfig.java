package com.epayment.core.infra;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
  public @Bean PasswordEncoder provideBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

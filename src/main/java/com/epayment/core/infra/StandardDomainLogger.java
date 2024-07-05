package com.epayment.core.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.epayment.core.domain.DomainLogger;

@Component
public class StandardDomainLogger implements DomainLogger {
  private static Logger log = LoggerFactory.getLogger(DomainLogger.class);

  public void info(String message) {
    log.info(message);    
  }

  public void info(String format, Object... arguments) {
    log.info(format, arguments);
  }
}

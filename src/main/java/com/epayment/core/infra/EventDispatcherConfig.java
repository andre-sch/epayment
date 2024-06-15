package com.epayment.core.infra;

import com.epayment.core.domain.BalanceChanged;
import com.epayment.core.domain.EventDispatcher;
import com.epayment.core.adapters.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventDispatcherConfig {
  @Autowired KafkaProducer<BalanceChanged> kafkaProducer;

  @Bean
  public EventDispatcher<BalanceChanged> balanceChangedDispatcher() {
    var dispatcher = new EventDispatcher<BalanceChanged>();
    dispatcher.attach(kafkaProducer);
    return dispatcher;
  }
}

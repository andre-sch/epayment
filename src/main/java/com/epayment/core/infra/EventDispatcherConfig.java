package com.epayment.core.infra;

import com.epayment.core.domain.*;
import com.epayment.core.adapters.kafka.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class EventDispatcherConfig {
  @Autowired private BalanceChangedKafkaProducer balanceChangedKafkaProducer;
  @Autowired private AccountEventKafkaProducer accountCreatedKafkaProducer;

  @Bean
  public EventDispatcher<BalanceChanged> balanceChangedDispatcher() {
    return dispatcherWithOneKafkaProducer(balanceChangedKafkaProducer);
  }
  
  @Bean
  public EventDispatcher<AccountEvent> accountCreatedDispatcher() {
    return dispatcherWithOneKafkaProducer(accountCreatedKafkaProducer);
  }

  private <Event> EventDispatcher<Event> dispatcherWithOneKafkaProducer(
    KafkaProducer<Event> producer
  ) {
    var dispatcher = new EventDispatcher<Event>();
    dispatcher.attach(producer);
    return dispatcher;
  }
}

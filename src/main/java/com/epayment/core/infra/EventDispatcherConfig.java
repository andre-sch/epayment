package com.epayment.core.infra;

import com.epayment.core.domain.*;
import com.epayment.core.adapters.kafka.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Configuration
public class EventDispatcherConfig {
  @Autowired private KafkaProducer<AccountEvent> accountEventKafkaProducer;
  @Autowired private KafkaProducer<BalanceChanged> balanceChangedKafkaProducer;
  @Autowired private KafkaProducer<TransactionFailed> transactionFailedKafkaProducer;
  
  @Bean
  public EventDispatcher<AccountEvent> accountCreatedDispatcher() {
    return dispatcherWithOneKafkaProducer(accountEventKafkaProducer);
  }

  @Bean
  public EventDispatcher<BalanceChanged> balanceChangedDispatcher() {
    return dispatcherWithOneKafkaProducer(balanceChangedKafkaProducer);
  }

  @Bean
  public EventDispatcher<TransactionFailed> transactionFailedDispatcher() {
    return dispatcherWithOneKafkaProducer(transactionFailedKafkaProducer);
  }

  private <Event> EventDispatcher<Event> dispatcherWithOneKafkaProducer(
    KafkaProducer<Event> producer
  ) {
    var dispatcher = new EventDispatcher<Event>();
    dispatcher.attach(producer);
    return dispatcher;
  }
}

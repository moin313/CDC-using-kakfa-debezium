package com.moin.services.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  
  @Value("${rabbitmq.exchange.notification}")
  private String notifictionExchange;
  
  @Value("${rabbitmq.queue.user.notification}")
  private String userNotificationQueue;
  
  @Value("${rabbitmq.routing.key.user.notification}")
  private String userNotifiationRoutingKey;
  
  @Bean
  Queue userNotificationQueue() {
    return QueueBuilder.durable(userNotificationQueue).build();
  }
  
  @Bean
  DirectExchange mainExchange() {
    return new DirectExchange(notifictionExchange);
  }
  
  @Bean
  Binding jobOrderUploadreportBinding() {
    return BindingBuilder.bind(userNotificationQueue()).to(mainExchange())
        .with(userNotifiationRoutingKey);
  }
}

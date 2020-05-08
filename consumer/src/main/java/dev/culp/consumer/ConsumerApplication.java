package dev.culp.consumer;

import dev.culp.producer.Foo;
import java.util.concurrent.CountDownLatch;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@SpringBootApplication
public class ConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }

  @Bean
  CountDownLatch latch() {
    return new CountDownLatch(1);
  }

  @Bean
  MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue("foo"),
      exchange = @Exchange("foo"),
      key = "foo"
  ))
  public void listen(Foo foo) {
    latch().countDown();
    LoggerFactory.getLogger(getClass()).info("received: {}", foo);
  }
}

package dev.culp.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

@SpringBootTest(properties = {"stubrunner.amqp.enabled=true"})
@AutoConfigureMessageVerifier
public abstract class Base {

  @Autowired
  RabbitTemplate rabbitTemplate;

  void foo() {
    rabbitTemplate.convertAndSend("foo", "foo", new Foo("baz"));
  }
}

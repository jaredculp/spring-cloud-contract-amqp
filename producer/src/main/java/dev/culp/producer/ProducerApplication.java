package dev.culp.producer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@SpringBootApplication
public class ProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }

  @Bean
  MessageConverter messageConverter() {
    final MessageConverter delegate = new Jackson2JsonMessageConverter();
    return new MessageConverter() {
      @Override
      public Message toMessage(Object o, MessageProperties messageProperties)
          throws MessageConversionException {
        final Message m = delegate.toMessage(o, messageProperties);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (GZIPOutputStream gz = new GZIPOutputStream(os)) {
          gz.write(m.getBody());
        } catch (IOException e) {
          throw new MessageConversionException(e.getMessage());
        }

        messageProperties.setHeader("GZIP-Compression", true);
        return new Message(os.toByteArray(), messageProperties);
      }

      @Override
      public Object fromMessage(Message message) throws MessageConversionException {
        return message.getBody();
      }
    };
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }
}

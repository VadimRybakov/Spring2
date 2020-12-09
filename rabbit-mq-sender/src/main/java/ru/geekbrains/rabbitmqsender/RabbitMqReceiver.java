package ru.geekbrains.rabbitmqsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.geekbrains.rabbitmqsender.dto.Message;

@Slf4j
@RabbitListener(queues = "#{'${user.name}' + '.queue'}")
public class RabbitMqReceiver {

  @RabbitHandler
  public void receive(Message msg) {
    log.info("New message '{}' from user '{}'", msg.getText(), msg.getSender());
  }
}

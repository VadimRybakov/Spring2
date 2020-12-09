package ru.geekbrains.rabbitmqsender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.rabbitmqsender.dto.Message;

@RestController
@RequiredArgsConstructor
public class SendMessageController {

  private final AmqpTemplate rabbitTemplate;

  @Value("${user.name}")
  private String userName;

  @PostMapping("send")
  public String sendMessage(@RequestParam("user") String user,
      @RequestBody String msg) {
    rabbitTemplate.convertAndSend("users.exchange", user, new Message(userName, msg));
    return "Message '" + msg + "' sent from user '" + userName + "' to user" + user + "' with RabbitMQ";
  }

}

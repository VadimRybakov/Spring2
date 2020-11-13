package ru.geekbrains.controllers;

import lombok.Data;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.geekbrains.dto.ChatMessage;

@Data
@Controller
public class WebSocketController {

  private final SimpMessagingTemplate template;

  @MessageMapping("/send_message")
  public void messageReceiver(@Payload ChatMessage chatMessage,
      SimpMessageHeaderAccessor headerAccessor) {
    if (headerAccessor.getUser() == null) {
      return;
    }
    template.convertAndSendToUser(headerAccessor.getUser().getName(), "/chat_out/receive_message",
        new ChatMessage("Server", "Answer to: " + chatMessage.getMessage()),
        createHeaders(headerAccessor.getSessionId()));
  }

  @GetMapping("/test/message")
  public void sendMessage() {
    template.convertAndSend("/chat_out/receive_message",
        new ChatMessage("Server", "Test message"));
  }

  private MessageHeaders createHeaders(String sessionId) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
        .create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);
    headerAccessor.setLeaveMutable(true);
    return headerAccessor.getMessageHeaders();
  }
}

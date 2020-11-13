package ru.geekbrains.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChatMessage {

  private String username;

  private String message;

  private LocalDateTime localDateTime;

  public ChatMessage() {
    this.localDateTime = LocalDateTime.now();
  }

  public ChatMessage(String server, String s) {
    this();
    this.username = username;
    this.message = message;
  }
}

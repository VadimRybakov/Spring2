package ru.geekbrains.exceptions;

public class NotFoundException extends RuntimeException {

  private String field = "FOUND NOTHING";

  public String getField() {
    return field;
  }
}

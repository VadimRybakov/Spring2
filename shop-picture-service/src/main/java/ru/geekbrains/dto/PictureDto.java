package ru.geekbrains.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.model.Picture;

@Data
@NoArgsConstructor
public class PictureDto implements Serializable {

  private Integer id;

  private String name;

  private String contentType;

  public PictureDto(Picture picture) {
    this.id = picture.getId();
    this.name = picture.getName();
    this.contentType = picture.getContentType();
  }
}

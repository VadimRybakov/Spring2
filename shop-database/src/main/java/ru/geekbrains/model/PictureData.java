package ru.geekbrains.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pictures_data")
public class PictureData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Lob
  @Type(type = "org.hibernate.type.BinaryType") // для правильной работы PostgreSQL
  @Column(name = "data", length = 33554430) // для правильной hibernate-валидации в MySQL
  private byte[] data;

  @Column
  private String fileName;

  public PictureData(byte[] data) {
    this.data = data;
  }

  public PictureData(String fileName) {
    this.fileName = fileName;
  }

}
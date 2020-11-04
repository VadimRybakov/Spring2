package ru.geekbrains.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "content_type", nullable = false)
  private String contentType;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
  @JoinColumn(name = "picture_data_id")
  private PictureData pictureData;

  @ManyToMany(mappedBy = "pictures")
  private List<Product> products;

  public Picture(String name, String contentType, PictureData pictureData) {
    this.name = name;
    this.contentType = contentType;
    this.pictureData = pictureData;
  }
}

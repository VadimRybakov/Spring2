package ru.geekbrains.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @OneToMany(
      mappedBy = "category",
      cascade = CascadeType.ALL)
  private List<Product> products;

}

package ru.geekbrains.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String title;

  @Column(name = "price")
  private BigDecimal price;

  @ManyToOne(optional = false)
  private Category category;

  @ManyToOne(optional = false)
  private Brand brand;

}
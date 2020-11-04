package ru.geekbrains.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Product;

@Data
@NoArgsConstructor
public class BrandDto implements Serializable {

  private Integer id;
  private String title;
  private List<Product> products;

  public BrandDto(Brand brand) {
    this.id = brand.getId();
    this.title = brand.getTitle();
    this.products = brand.getProducts();
  }
}

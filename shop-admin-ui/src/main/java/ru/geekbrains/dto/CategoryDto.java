package ru.geekbrains.dto;


import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;

@Data
@NoArgsConstructor
public class CategoryDto {

  private Integer id;
  private String title;
  private List<Product> products;

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.title = category.getTitle();
    this.products = category.getProducts();
  }
}

package ru.geekbrains.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;

@Data
@NoArgsConstructor
public class ProductDto implements Serializable {

  private Integer id;

  private String title;

  private BigDecimal price;

  private Category category;

  private Brand brand;

  private List<PictureDto> pictures;

  private MultipartFile[] newPictures;

  public ProductDto(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.price = product.getPrice();
    this.category = product.getCategory();
    this.brand = product.getBrand();
    this.pictures = product.getPictures().stream()
        .map(PictureDto::new)
        .collect(Collectors.toList());
  }
}

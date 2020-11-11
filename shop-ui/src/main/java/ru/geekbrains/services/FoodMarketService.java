package ru.geekbrains.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.repo.ProductRepository;

@Service
public class FoodMarketService {

  private ProductRepository productRepository;

  @Autowired
  public void setProductRepository(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<ProductDto> findAll() {
    return productRepository.findAll().stream()
        .map(ProductDto::new)
        .collect(Collectors.toList());
  }

  public ProductDto findOneById(Integer id) {
    return productRepository.findOneById(id)
        .map(ProductDto::new)
        .orElseThrow(IllegalArgumentException::new);
  }
}

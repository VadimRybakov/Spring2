package ru.geekbrains.services;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repo.ProductSpecification;

@Service
public class ProductService {

  private ProductRepository productRepository;

  @Autowired
  public void setProductRepository(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product findById(int id) {
    return productRepository.findById(id).orElseThrow(
        NotFoundException::new);
  }

  @Transactional
  public void delete(Product product) {
    productRepository.delete(product);
  }

  @Transactional
  public void update(Product product) {
    productRepository.saveAndFlush(product);
  }

  public Page<Product> findByFilters(BigDecimal min, BigDecimal max, PageRequest page) {

    Specification<Product> spec = ProductSpecification.trueLiteral();

    if (max == null && min != null) {
      spec = spec.and(ProductSpecification.filterByMinPrice(min));
    } else if (max != null && min == null) {
      spec = spec.and(ProductSpecification.filterByMaxPrice(max));
    } else if (max != null && min != null) {
      spec = spec.and(ProductSpecification.filterByMinAndMaxPrice(min, max));
    }
    return productRepository.findAll(spec, page);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @Transactional
  public void save(Product product) {
    productRepository.save(product);
  }

  @Transactional
  public void deleteById(int id) {
    productRepository.deleteById(id);
  }
}

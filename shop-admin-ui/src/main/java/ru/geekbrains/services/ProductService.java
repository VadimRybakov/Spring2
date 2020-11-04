package ru.geekbrains.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repo.ProductSpecification;
import ru.geekbrains.service.PictureService;

@Slf4j
@Service
public class ProductService {

  private ProductRepository productRepository;
  private PictureService pictureService;

  @Autowired
  public void setProductRepository(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Autowired
  public void setPictureService(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  public ProductDto findById(int id) {
    return productRepository.findById(id).map(ProductDto::new).orElseThrow(
        NotFoundException::new);
  }

  @Transactional
  @SneakyThrows
  public void update(ProductDto productDto) {
    Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
        .orElseThrow(NotFoundException::new) : new Product();
    product.setTitle(productDto.getTitle());
    product.setCategory(productDto.getCategory());
    product.setBrand(productDto.getBrand());
    product.setPrice(productDto.getPrice());
    if (productDto.getNewPictures() != null) {
      for (MultipartFile newPicture : productDto.getNewPictures()) {
        log.info("Product {} file {} size {}", product.getId(),
            newPicture.getOriginalFilename(), newPicture.getSize());

        if (product.getPictures() == null) {
          product.setPictures(new ArrayList<>());
        }

        product.getPictures().add(new Picture(
            newPicture.getOriginalFilename(),
            newPicture.getContentType(),
            pictureService.createPictureData(newPicture.getBytes())));
      }
    }
    productRepository.save(product);
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

  public void deleteById(int id) {
    productRepository.deleteById(id);
  }
}

package ru.geekbrains.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.BrandDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.model.Brand;
import ru.geekbrains.repo.BrandRepository;

@Service
public class BrandService {

  private BrandRepository brandRepository;

  @Autowired
  public void setBrandRepository(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  public Page<Brand> findByFilters(PageRequest pageRequest) {
    return brandRepository.findAll(pageRequest);
  }

  public BrandDto findById(int id) {
    return brandRepository.findById(id).map(BrandDto :: new).orElseThrow(
        NotFoundException::new);
  }

  @Transactional
  public void update(BrandDto brandDto) {
    Brand brand = new Brand();
    brand.setId(brandDto.getId());
    brand.setTitle(brandDto.getTitle());
    brand.setProducts(brandDto.getProducts());
    brandRepository.saveAndFlush(brand);
  }

  public List<Brand> findAll() {
    return brandRepository.findAll();
  }

  @Transactional
  public void deleteById(int id) { brandRepository.deleteById(id);
  }
}

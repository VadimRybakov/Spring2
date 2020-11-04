package ru.geekbrains.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.model.Category;
import ru.geekbrains.repo.CategoryRepository;

@Service
public class CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  public void setCategoryRepository(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Page<Category> findByFilters(PageRequest pageRequest) {
    return categoryRepository.findAll(pageRequest);
  }

  public CategoryDto findById(int id) {
    return categoryRepository.findById(id).map(CategoryDto::new).orElseThrow(
        NotFoundException::new);
  }

  @Transactional
  public void update(CategoryDto categoryDto) {
    Category category = new Category();
    category.setId(categoryDto.getId());
    category.setTitle(categoryDto.getTitle());
    category.setProducts(categoryDto.getProducts());
    categoryRepository.saveAndFlush(category);
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  @Transactional
  public void deleteById(int id) {
    categoryRepository.deleteById(id);
  }
}

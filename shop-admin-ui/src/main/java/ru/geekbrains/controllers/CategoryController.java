package ru.geekbrains.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.model.Category;
import ru.geekbrains.services.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

  private CategoryService categoryService;

  @Autowired
  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public String getAllCategories(Model model,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size,
      @RequestParam("sortBy") Optional<String> sortBy,
      @RequestParam("direction") Optional<String> direction
  ) {
    Sort sort;
    String param;
    if (sortBy.isPresent()) {
      if (sortBy.get().equals("title")) {
        param = "title";
      } else {
        param = "id";
      }
    } else {
      param = "id";
    }
    if (direction.isPresent()) {
      if (direction.get().equals("DESC")) {
        sort = Sort.by(Sort.Direction.DESC, param);
      } else {
        sort = Sort.by(Sort.Direction.ASC, param);
      }
    } else {
      sort = Sort.by(Sort.Direction.ASC, param);
    }
    PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
    Page<Category> categories = categoryService.findByFilters(pageRequest);
    model.addAttribute("categories", categories);
    return "categories";
  }

  @GetMapping("/edit/{id}")
  public String editBrand(@PathVariable("id") int id, Model model) {
    CategoryDto category = categoryService.findById(id);
    model.addAttribute("category", category);
    return "categories_form";
  }

  @GetMapping("/edit/form")
  public String productForm(Model model) {
    model.addAttribute("category", new Category());
    return "categories_form";
  }

  @GetMapping("/edit/delete/{id}")
  public String deleteBrand(@PathVariable("id") int id) {
    categoryService.deleteById(id);
    return "redirect:/categories";
  }

  @PostMapping("/edit/update")
  public String updateBrand(CategoryDto categoryDto) {
    categoryService.update(categoryDto);
    return "redirect:/categories";
  }
}

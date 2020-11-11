package ru.geekbrains.controllers;

import java.math.BigDecimal;
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
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.model.Product;
import ru.geekbrains.services.BrandService;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

  private ProductService productService;
  private BrandService brandService;
  private CategoryService categoryService;

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Autowired
  public void setBrandService(BrandService brandService) {
    this.brandService = brandService;
  }

  @Autowired
  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public String getAllProducts(Model model,
      @RequestParam(name = "min_price", required = false) BigDecimal min,
      @RequestParam(name = "max_price", required = false) BigDecimal max,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size,
      @RequestParam("sortBy") Optional<String> sortBy,
      @RequestParam("direction") Optional<String> direction
  ) {
    Sort sort;
    String param;
    if (sortBy.isPresent()) {
      if (sortBy.get().equals("price")) {
        param = "price";
      } else if (sortBy.get().equals("title")) {
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
    Page<Product> products = productService.findByFilters(min, max, pageRequest);
    model.addAttribute("products", products);
    return "products";
  }

  @GetMapping("/edit/{id}")
  public String editProduct(@PathVariable("id") int id, Model model) {
    ProductDto product = productService.findById(id);
    model.addAttribute("product", product);
    model.addAttribute("brands", brandService.findAll());
    model.addAttribute("categories", categoryService.findAll());
    return "products_form";
  }

  @GetMapping("/edit/form")
  public String productForm(Model model) {
    model.addAttribute("product", new ProductDto());
    model.addAttribute("brands", brandService.findAll());
    model.addAttribute("categories", categoryService.findAll());
    return "products_form";
  }

  @GetMapping("/edit/delete/{id}")
  public String deleteProduct(@PathVariable("id") int id) {
    productService.deleteById(id);
    return "redirect:/products";
  }

  @PostMapping("/edit/update")
  public String updateProduct(ProductDto productDto) {
    productService.update(productDto);
    return "redirect:/products";
  }
}

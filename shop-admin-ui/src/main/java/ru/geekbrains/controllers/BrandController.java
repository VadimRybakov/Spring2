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
import ru.geekbrains.model.Brand;
import ru.geekbrains.services.BrandService;

@Controller
@RequestMapping("/brands")
public class BrandController {

  private BrandService brandService;

  @Autowired
  public void setBrandService(BrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping
  public String getAllBrands(Model model,
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
    Page<Brand> brands = brandService.findByFilters(pageRequest);
    model.addAttribute("brands", brands);
    return "brands";
  }

  @GetMapping("/edit/{id}")
  public String editBrand(@PathVariable("id") int id, Model model) {
    Brand brand = brandService.findById(id);
    model.addAttribute("brand", brand);
    return "brands_form";
  }

  @GetMapping("/edit/form")
  public String productForm(Model model) {
    model.addAttribute("brand", new Brand());
    return "brands_form";
  }

  @GetMapping("/edit/delete/{id}")
  public String deleteBrand(@PathVariable("id") int id) {
    Brand brand = brandService.findById(id);
    brandService.delete(brand);
    return "redirect:/brands";
  }

  @PostMapping("/edit/update")
  public String updateBrand(Brand brand) {
    brandService.update(brand);
    return "redirect:/brands";
  }
}

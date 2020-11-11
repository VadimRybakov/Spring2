package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.services.FoodMarketService;

@Controller
public class FoodMarketController {

  private FoodMarketService foodService;

  @Autowired
  public void setFoodService(FoodMarketService foodService) {
    this.foodService = foodService;
  }

  @GetMapping("/")
  public String getAllProducts(Model model) {
    model.addAttribute("products", foodService.findAll());
    return "index";
  }

  @GetMapping("/{id}")
  public String getProductById(@PathVariable("id") Integer id, Model model) {
    model.addAttribute("product", foodService.findOneById(id));
    return "product";
  }
}

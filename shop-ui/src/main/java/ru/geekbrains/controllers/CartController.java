package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.dto.SessionData;
import ru.geekbrains.services.CartService;
import ru.geekbrains.services.FoodMarketService;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final FoodMarketService foodMarketService;
  private final CartService cartService;

  @GetMapping
  public String getProducts(Model model) {
    model.addAttribute("sessionDatas", cartService.getSessionDatas());
    return "cart";
  }

  @PostMapping
  public String updateCart(SessionData sessionData) {
    sessionData.setProductDto(foodMarketService.findOneById(sessionData.getProductId()));
    cartService.updateCart(sessionData);
    return "redirect:/cart";
  }

  @PostMapping("/delete")
  public String deleteLineItem(SessionData sessionData) {
    cartService.removeProduct(sessionData);
    return "redirect:/cart";
  }

}

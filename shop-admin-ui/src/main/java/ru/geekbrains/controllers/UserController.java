package ru.geekbrains.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.model.Role;
import ru.geekbrains.model.User;
import ru.geekbrains.services.RoleService;
import ru.geekbrains.services.UserService;

@Controller
public class UserController {

  private UserService userService;
  private RoleService roleService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setRoleService(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping("/login")
  public String showMyLoginPage() {
    return "login";
  }

  @GetMapping("/users")
  public String getUsers(Model model) {
    List<User> users = userService.findAll();
    model.addAttribute("users", users);
    return "users";
  }

  @GetMapping("users/edit")
  public String createUser(Model model) {
    List<Role> roles = roleService.findAll();
    model.addAttribute("roles", roles);
    model.addAttribute("user", new User());
    return "user";
  }

  @GetMapping("/users/edit/{id}")
  public String editUser(Model model, @PathVariable("id") int id) {
    UserDto user = userService.findById(id);
    List<Role> roles = roleService.findAll();
    model.addAttribute("user", user);
    model.addAttribute("roles", roles);
    return "user";
  }

  @GetMapping("/users/edit/delete/{id}")
  public String deleteUser(@PathVariable("id") int id) {
    userService.deleteById(id);
    return "redirect:/users";
  }

  @PostMapping("/users/edit/update")
  public String saveUser(@ModelAttribute(name = "user") UserDto userDto, Model model) {
    boolean passMismatch = userDto.getPassword().equals(userDto.getConfirmPassword());
    if (!passMismatch) {
      String pasMismatchErr = "passwordsNotMatchException";
      List<Role> roles = roleService.findAll();
      model.addAttribute("pasMismatchErr", pasMismatchErr);
      model.addAttribute("user", userDto);
      model.addAttribute("roles", roles);
      return "user";
    } else {
      User existUser = userService.findByName(userDto.getUsername());
        if (existUser != null) {
            userService.deleteUser(existUser);
        }
      userService.save(userDto);
    }
    return "redirect:/users";
  }
}

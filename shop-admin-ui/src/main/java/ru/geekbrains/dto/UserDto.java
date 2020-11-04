package ru.geekbrains.dto;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.model.Role;
import ru.geekbrains.model.User;

@Data
@NoArgsConstructor
public class UserDto {
  private Integer id;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @NotEmpty
  private String confirmPassword;

  private String firstName;

  private String lastName;

  private String email;

  private Set<Role> roles;

  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getName();
    this.password = user.getPassword();
    this.email = user.getEmail();
    this.roles = user.getRoles();
  }
}

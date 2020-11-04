package ru.geekbrains.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.geekbrains.services.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/users", "/picture").hasAnyRole("ADMIN", "SUPERADMIN")
        .antMatchers("/users/edit").hasRole("SUPERADMIN")
        .antMatchers("/brands/edit/**", "/categories/edit**", "/products/edit/**")
        .hasAnyRole("MANAGER", "ADMIN", "SUPERADMIN")
        .antMatchers("/brands", "/products", "/categories").authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/authenticateTheUser")
        .and()
        .logout()
        .logoutSuccessUrl("/products")
        .permitAll();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userService);
    auth.setPasswordEncoder(passwordEncoder());
    return auth;
  }
}

package ru.geekbrains.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.model.Role;

import ru.geekbrains.model.User;
import ru.geekbrains.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setPasswordEncoder(
      PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String name) {
    User user = userRepository.findUserByName(name).orElseThrow(() ->
        new UsernameNotFoundException("User not found"));
    return new org.springframework.security.core.userdetails.User(
        user.getName(),
        user.getPassword(),
        mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public UserDto findById(int id) {
    return userRepository.findById(id).map(UserDto::new).orElseThrow(
        NotFoundException::new);
  }

  public User findByName(String name) {
    return userRepository.findUserByName(name).orElseThrow(() ->
        new UsernameNotFoundException("User not found"));
  }

  @Transactional
  public void deleteById(int id) {
    userRepository.deleteById(id);
  }

  @Transactional
  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  @Transactional
  public void save(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setEmail(userDto.getEmail());
    user.setRoles(userDto.getRoles());
    userRepository.save(user);
  }
}

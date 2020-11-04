package ru.geekbrains.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findUserByName(String name);
}

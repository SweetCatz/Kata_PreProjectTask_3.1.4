package ru.kata.spring.boot.javascript.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot.javascript.demo.entity.User;
import java.util.Optional;

@Repository
public interface UsersRepositories extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

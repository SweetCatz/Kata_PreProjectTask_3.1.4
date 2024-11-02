package ru.kata.spring.boot.javascript.demo.service;

import ru.kata.spring.boot.javascript.demo.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void update(Long id, User user);

    void delete(Long id);
}

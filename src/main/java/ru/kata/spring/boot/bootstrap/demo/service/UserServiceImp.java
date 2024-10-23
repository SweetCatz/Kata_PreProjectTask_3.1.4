package ru.kata.spring.boot.bootstrap.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot.bootstrap.demo.entity.User;
import ru.kata.spring.boot.bootstrap.demo.repositories.UsersRepositories;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final UsersRepositories usersRepositories;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UsersRepositories usersRepositories, PasswordEncoder passwordEncoder) {
        this.usersRepositories = usersRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return usersRepositories.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = usersRepositories.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepositories.save(user);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        usersRepositories.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        usersRepositories.deleteById(id);
    }
}

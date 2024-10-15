package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepositories;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final UsersRepositories usersRepositories;

    @Autowired
    public UserServiceImp(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    @Override
    public List<User> findAll() {
        return usersRepositories.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = usersRepositories.findByName(username);
        if (userOptional.isPresent()) {
            return new UserDetailsImp(userOptional.get());
        } else {
            throw new NoSuchElementException(String.format("User '%s' not found", username));
        }
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
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepositories.save(user);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

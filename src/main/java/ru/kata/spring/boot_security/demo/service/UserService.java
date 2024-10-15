package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findById(Long id);

    void save(User user);

    void update(Long id, User user);

    void delete(Long id);
}

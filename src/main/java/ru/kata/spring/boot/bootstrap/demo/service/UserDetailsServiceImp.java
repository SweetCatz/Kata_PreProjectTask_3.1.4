package ru.kata.spring.boot.bootstrap.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot.bootstrap.demo.entity.User;
import ru.kata.spring.boot.bootstrap.demo.repositories.UsersRepositories;
import ru.kata.spring.boot.bootstrap.demo.security.UserDetailsImp;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImp implements UserDetailsService {
    private final UsersRepositories usersRepositories;

    @Autowired
    public UserDetailsServiceImp(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = usersRepositories.findByEmail(email);
        if (userOptional.isPresent()) {
            return new UserDetailsImp(userOptional.get());
        } else {
            throw new NoSuchElementException(String.format("User '%s' not found", email));
        }
    }
}

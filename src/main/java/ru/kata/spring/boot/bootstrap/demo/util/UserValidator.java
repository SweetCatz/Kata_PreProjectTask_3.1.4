package ru.kata.spring.boot.bootstrap.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot.bootstrap.demo.entity.User;
import ru.kata.spring.boot.bootstrap.demo.repositories.UsersRepositories;
import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UsersRepositories usersRepositories;

    @Autowired
    public UserValidator(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getId() == null) {
            if (usersRepositories.findByEmail(user.getEmail()).isPresent()) {
                errors.rejectValue("email", "", "This email address is already in use");
            }
        } else {
            Optional<User> userOptional = usersRepositories.findById(user.getId());
            if (userOptional.isPresent()) {
                User userFromDB = userOptional.get();
                if (!userFromDB.getEmail().equals(user.getEmail())) {
                    errors.rejectValue("email", "", "This email address is already in use");
                }
            }
        }
    }
}
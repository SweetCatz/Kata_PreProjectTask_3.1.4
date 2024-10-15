package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepositories;
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
        try {
            emailValidation(user);
        } catch (EmailAlreadyUsedException e) {
            errors.rejectValue("email", "", "This email address is already in use");
        }
        try {
          userNameValidation(user);
        } catch (UserNameAlreadyUsedException e) {
            errors.rejectValue("name", "", "This name is already in use");
        }
    }

    public void emailValidation(User user) {
        if (user.getId() == null) {
            if (usersRepositories.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyUsedException("This email address is already in use");
            }
        } else {
            Optional<User> userOptional = usersRepositories.findById(user.getId());
            if (userOptional.isPresent()) {
                User userFromDB = userOptional.get();
                if (!userFromDB.getEmail().equals(user.getEmail())) {
                    throw new EmailAlreadyUsedException("This email address is already in use");
                }
            }
        }
    }

    public void userNameValidation(User user) {
        if (user.getId() == null) {
            if (usersRepositories.findByName(user.getName()).isPresent()) {
                throw new UserNameAlreadyUsedException("This username is already in use");
            }
        } else {
            Optional<User> userOptional = usersRepositories.findById(user.getId());
            if (userOptional.isPresent()) {
                User userFromDB = userOptional.get();
                if (!userFromDB.getName().equals(user.getName())) {
                    throw new UserNameAlreadyUsedException("This username is already in use");
                }
            }
        }
    }
}
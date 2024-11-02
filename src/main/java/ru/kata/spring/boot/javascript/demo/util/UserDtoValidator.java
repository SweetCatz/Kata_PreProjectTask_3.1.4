package ru.kata.spring.boot.javascript.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot.javascript.demo.dto.UserDto;
import ru.kata.spring.boot.javascript.demo.entity.User;
import ru.kata.spring.boot.javascript.demo.repositories.UsersRepositories;
import java.util.Optional;

@Component
public class UserDtoValidator implements Validator {
    private final UsersRepositories usersRepositories;

    @Autowired
    public UserDtoValidator(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        if (userDto.getId() == null) {
            if (usersRepositories.findByEmail(userDto.getEmail()).isPresent()) {
                errors.rejectValue("email", "", "This email address is already in use");
            }
        } else {
            Optional<User> userOptional = usersRepositories.findById(userDto.getId());
            if (userOptional.isPresent()) {
                User userFromDB = userOptional.get();
                if (!userFromDB.getEmail().equals(userDto.getEmail())) {
                    errors.rejectValue("email", "", "This email address is already in use");
                }
            }
        }
    }
}
package ru.kata.spring.boot_security.demo.util;

public class UserNameAlreadyUsedException extends RuntimeException {
    public UserNameAlreadyUsedException(String message) {
        super(message);
    }
}

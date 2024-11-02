package ru.kata.spring.boot.javascript.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot.javascript.demo.entity.User;
import ru.kata.spring.boot.javascript.demo.security.UserDetailsImp;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication auth) {
        UserDetailsImp personDetails = (UserDetailsImp) auth.getPrincipal();
        return new ResponseEntity<>(personDetails.getUser(), HttpStatus.OK);
    }
}

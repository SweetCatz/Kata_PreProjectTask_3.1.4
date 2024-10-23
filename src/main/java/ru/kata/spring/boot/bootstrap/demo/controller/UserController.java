package ru.kata.spring.boot.bootstrap.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot.bootstrap.demo.security.UserDetailsImp;

@Controller
public class UserController {
    @GetMapping("/user")
    public String infoAboutUserPage(Authentication authentication, Model model) {
        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
        model.addAttribute("currentUser", userDetailsImp.getUser());
        return "user";
    }
}
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;

@Controller
public class UserController {
    @GetMapping("/user")
    public String infoAboutUserPage(Authentication authentication, Model model) {
        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
        boolean adminCheck = AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ROLE_ADMIN");
        model.addAttribute("adminCheck", adminCheck);
        model.addAttribute("user", userDetailsImp.getUser());
        return "user/user";
    }
}
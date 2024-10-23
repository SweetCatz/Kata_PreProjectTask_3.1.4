package ru.kata.spring.boot.bootstrap.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot.bootstrap.demo.entity.User;
import ru.kata.spring.boot.bootstrap.demo.security.UserDetailsImp;
import ru.kata.spring.boot.bootstrap.demo.service.RoleService;
import ru.kata.spring.boot.bootstrap.demo.service.UserService;
import ru.kata.spring.boot.bootstrap.demo.util.UserValidator;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    private List<String> errorsList;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
        this.errorsList = new ArrayList<>();
    }

    @GetMapping
    public String showAllUsersPage(Authentication auth, ModelMap model) {
        UserDetailsImp userDetailsImp = (UserDetailsImp) auth.getPrincipal();
        model.addAttribute("newUser", new User());
        model.addAttribute("currentUser", userDetailsImp.getUser());
        model.addAttribute("rolesAll", roleService.findAll());
        model.addAttribute("usersAll", userService.findAll());
        model.addAttribute("errors", errorsList);
        return "admin";
    }

    @PostMapping("/new")
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            errorsList.add("Oops! ERROR SAVING NEW USER IN DATABASE - INVALID FIELD VALUES!");
            for (FieldError fieldError : result.getFieldErrors()) {
                errorsList.add(fieldError.getDefaultMessage());
            }
            return "redirect:/admin";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            errorsList.add("Oops! ERROR UPDATE CURRENT USER IN DATABASE - INVALID FIELD VALUES!");
            for (FieldError fieldError : result.getFieldErrors()) {
                errorsList.add(fieldError.getDefaultMessage());
            }
            return "redirect:/admin";
        }
        userService.update(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsersPage(ModelMap model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());
        return "admin/admin";
    }

    @GetMapping("/new")
    public String showNewUserPage(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/new";
    }

    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "/admin/new";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditUserPage(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "admin/edit";
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
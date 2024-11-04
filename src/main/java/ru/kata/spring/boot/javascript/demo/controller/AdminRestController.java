package ru.kata.spring.boot.javascript.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot.javascript.demo.dto.UserDto;
import ru.kata.spring.boot.javascript.demo.entity.Role;
import ru.kata.spring.boot.javascript.demo.entity.User;
import ru.kata.spring.boot.javascript.demo.service.RoleService;
import ru.kata.spring.boot.javascript.demo.service.UserService;
import ru.kata.spring.boot.javascript.demo.util.UserDtoValidator;
import ru.kata.spring.boot.javascript.demo.util.UserNotFoundException;
import ru.kata.spring.boot.javascript.demo.util.UserDtoValidationException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserDtoValidator userDtoValidator;

    @Autowired
    public AdminRestController(UserService userService, UserDtoValidator userValidator,
                               RoleService roleService) {
        this.userService = userService;
        this.userDtoValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll().stream()
                .map(this::convertToUserDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> showUserById(@PathVariable Long id) {
        return new ResponseEntity<>(convertToUserDto(userService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserDto userDto,
                                                 BindingResult bindingResult) {
        userDtoValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new UserDtoValidationException(bindingResult.getFieldErrors());
        }
        userService.save(convertToUser(userDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDto userDto,
                                                 BindingResult bindingResult) {
        userDtoValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new UserDtoValidationException(bindingResult.getFieldErrors());
        }
        userService.update(userDto.getId(), convertToUser(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<Map<String,String>> handleException(UserDtoValidationException e) {
        Map<String, String> errorsMap = new HashMap<>();
        for (FieldError error : e.getValidationErrors()) {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    private User convertToUser(UserDto userDto) {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        userDto.getRoles().forEach(role -> roles.add(roleService.findByRoleName("ROLE_".concat(role))));
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(roles);
        return user;
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::toString).collect(Collectors.toList()));
        return userDto;
    }
}
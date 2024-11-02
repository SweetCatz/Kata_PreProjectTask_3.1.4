package ru.kata.spring.boot.javascript.demo.service;

import ru.kata.spring.boot.javascript.demo.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findByRoleName(String roleName);
}

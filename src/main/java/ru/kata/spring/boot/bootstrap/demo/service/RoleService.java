package ru.kata.spring.boot.bootstrap.demo.service;

import ru.kata.spring.boot.bootstrap.demo.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();
}

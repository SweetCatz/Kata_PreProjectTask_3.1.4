package ru.kata.spring.boot.bootstrap.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot.bootstrap.demo.entity.Role;
import ru.kata.spring.boot.bootstrap.demo.repositories.RolesRepositories;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {
    private final RolesRepositories rolesRepositories;

    @Autowired
    public RoleServiceImp(RolesRepositories rolesRepositories) {
        this.rolesRepositories = rolesRepositories;
    }

    @Override
    public List<Role> findAll() {
        return rolesRepositories.findAll();
    }
}

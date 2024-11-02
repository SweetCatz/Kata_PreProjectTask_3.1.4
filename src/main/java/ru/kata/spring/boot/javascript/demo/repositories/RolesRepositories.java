package ru.kata.spring.boot.javascript.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot.javascript.demo.entity.Role;

@Repository
public interface RolesRepositories extends JpaRepository<Role, Long> {
    Role findByRoleName(String name);
}

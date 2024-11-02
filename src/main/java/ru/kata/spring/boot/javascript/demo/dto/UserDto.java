package ru.kata.spring.boot.javascript.demo.dto;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private Long id;

    @Size(min = 2, max = 20, message = "The 'First Name' field must be kept between 2 and 20 characters")
    private String name;

    @Size(min = 2, max = 20, message = "The 'Last Name' field must contain from 2 to 20 characters")
    private String lastName;

    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 127, message = "Age must be less than 127")
    private byte age;

    @NotEmpty(message = "The 'EMAIL' field must not be empty")
    @Email(message = "The 'EMAIL' field must be valid")
    private String email;

    @Size(min = 4, message = "The 'PASSWORD' field must contain between 4 and 20 characters")
    private String password;

    private List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 20, message = "The 'Name' field must be kept between 2 and 20 characters") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 20, message = "The 'Name' field must be kept between 2 and 20 characters") String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 20, message = "The 'LastName' field must contain from 2 to 20 characters") String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(min = 2, max = 20, message = "The 'LastName' field must contain from 2 to 20 characters") String lastName) {
        this.lastName = lastName;
    }

    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 127, message = "Age must be less than 127")
    public byte getAge() {
        return age;
    }

    public void setAge(@Min(value = 1, message = "Age must be greater than 0") @Max(value = 127, message = "Age must be less than 127") byte age) {
        this.age = age;
    }

    public @NotEmpty(message = "The 'EMAIL' field must not be empty") @Email(message = "The 'EMAIL' field must be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "The 'EMAIL' field must not be empty") @Email(message = "The 'EMAIL' field must be valid") String email) {
        this.email = email;
    }

    public @Size(min = 4, message = "The 'PASSWORD' field must contain between 4 and 20 characters") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 4, message = "The 'PASSWORD' field must contain between 4 and 20 characters") String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return age == userDto.age && Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(lastName, userDto.lastName) && Objects.equals(email, userDto.email) && Objects.equals(password, userDto.password) && Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, age, email, password, roles);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}

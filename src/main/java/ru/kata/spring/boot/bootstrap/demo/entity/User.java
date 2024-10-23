package ru.kata.spring.boot.bootstrap.demo.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 20, message = "The 'Name' field must be kept between 2 and 20 characters")
    private String name;

    @Column(name = "last_name")
    @Size(min = 2, max = 20, message = "The 'LastName' field must contain from 2 to 20 characters")
    private String lastname;

    @Column(name = "age")
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 127, message = "Age must be less than 127")
    private byte age;

    @Column(name = "email")
    @NotEmpty(message = "The 'EMAIL' field must not be empty")
    @Email(message = "The 'EMAIL' field must be valid")
    private String email;

    @Column(name = "password")
    @Size(min = 4, message = "The 'PASSWORD' field must contain between 4 and 20 characters")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User() {
    }

    public User(String name, String lastname, byte age, String email, String password, List<Role> roles) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

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

    public @Size(min = 2, max = 20, message = "The 'LastName' field must contain from 2 to 20 characters") String getLastname() {
        return lastname;
    }

    public void setLastname(@Size(min = 2, max = 20, message = "The 'LastName' field must contain from 2 to 20 characters") String lastname) {
        this.lastname = lastname;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(name, user.name)
                && Objects.equals(lastname, user.lastname) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, age, email, password, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
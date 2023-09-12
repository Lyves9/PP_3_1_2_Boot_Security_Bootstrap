package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.data.EmailExistsException;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void saveUser(User user) throws EmailExistsException;

    void updateUser(User user);

    User getUser(Long id);

    void removeUser(Long id);

    User findByEmail(String email);
}

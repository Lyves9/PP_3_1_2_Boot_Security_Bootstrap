package ru.kata.spring.boot_security.demo.data;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserData {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserData(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createUsersRoles() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        roleRepository.save(role1);
        roleRepository.save(role2);

        User user = new User("user",new BCryptPasswordEncoder().encode("user"));
        User admin = new User("admin",new BCryptPasswordEncoder().encode("admin"));
        user.setRoles(new HashSet<>(Set.of(role2)));
        admin.setRoles(new HashSet<>(Set.of(role1)));

        userRepository.save(user);
        userRepository.save(admin);
    }
}

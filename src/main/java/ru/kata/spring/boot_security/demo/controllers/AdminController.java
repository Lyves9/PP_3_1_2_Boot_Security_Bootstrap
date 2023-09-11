package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/new")
    public String getAddNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allroles", roleService.getRoles());
        return "admin/user-create";
    }

    @PostMapping()
    public String getCreateForm(@ModelAttribute("user") User user, @RequestParam("rolesList") String[] selectedRoles) {
        userService.saveUser(user, selectedRoles);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}/edit")
    public String getEditForm(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getRoles());
        return "admin/user-edit";
    }

    @PatchMapping("/{id}")
    public String getUpdateForm(@ModelAttribute("user") User user, @PathVariable("id") Long id, @RequestParam("rolesList") String[] selectedRoles) {
        User newUser = userService.findById(id);
        userService.updateUser(user, newUser, selectedRoles);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String getDeleteForm(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/by_user_name")
    public String findByUserName(String username) {
        User user = userService.getUserByUsername(username);
        return user.getUsername();
    }
}

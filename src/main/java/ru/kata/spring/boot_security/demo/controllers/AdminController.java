package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, @ModelAttribute("newUser") User newUser, Principal principal) {
        User authenticatedUser = userService.findByEmail(principal.getName());
        model.addAttribute("user", authenticatedUser);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") User user,Model model) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            String errorMessage = "Email уже существует";
            model.addAttribute("errorMessage", errorMessage);
            return "admin";
        }
        return "redirect:/admin";
    }

    @PatchMapping("/admin/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

}
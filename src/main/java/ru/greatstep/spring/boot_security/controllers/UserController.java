package ru.greatstep.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.RoleRepository;
import ru.greatstep.spring.boot_security.repositories.UserRepository;
import ru.greatstep.spring.boot_security.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String snowUserList(Principal principal,Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "user";
    }
}

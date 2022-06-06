package ru.greatstep.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.UserRepository;
import ru.greatstep.spring.boot_security.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String snowUserList(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "index";
    }

    @GetMapping("/user_admin")
    public String snowUserList(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("activeUser",user);
        return "user_admin";
    }
}

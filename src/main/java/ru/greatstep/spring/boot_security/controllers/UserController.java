package ru.greatstep.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String snowActiveUserInfo(Principal principal,Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("activeUser",user);
        return "user";
    }
}

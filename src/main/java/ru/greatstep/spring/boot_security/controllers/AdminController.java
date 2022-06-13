package ru.greatstep.spring.boot_security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.service.UserServiceImp;


import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;


    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping()
    public String snowAdminPanel() {
        return "admin";
    }


    @GetMapping("/info")
    public String showActiveUserInfo(Principal principal, Model model) {
        User user = userServiceImp.findByUsername(principal.getName());
        model.addAttribute("activeUser", user);
        return "admin_info";
    }

}

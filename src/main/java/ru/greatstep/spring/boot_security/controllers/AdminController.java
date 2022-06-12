package ru.greatstep.spring.boot_security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring.boot_security.models.Role;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.RoleRepository;
import ru.greatstep.spring.boot_security.repositories.UserRepository;
import ru.greatstep.spring.boot_security.service.UserService;


import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    final
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String snowAdminPanel() {
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {

        model.addAttribute("newUser", new User());
        model.addAttribute("role", new ArrayList<Role>());
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("activeUser", user);

        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles) {

        ArrayList<Role> roleSet = userService.getRoleCollectionToStringArray(roles);
        roleRepository.saveAll(roleSet);
        user.setRoles(roleSet);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);


        return "redirect:/admin";
    }

    @GetMapping("/info")
    public String showActiveUserInfo(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("activeUser", user);
        return "admin_info";
    }

}

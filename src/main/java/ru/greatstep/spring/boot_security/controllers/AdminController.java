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


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping()
    public String snowUserList(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam (value = "role") String[] roles) {

        ArrayList<Role> roleSet = new ArrayList<>();
        for (String role:
             roles) {

            roleSet.add(new Role(role));
        }

        roleRepository.saveAll(roleSet);
        user.setRoles(roleSet);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);


        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userRepository.findUserById((long) id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam (value = "role") String[] roles) {
        ArrayList<Role> roleSet = new ArrayList<>();
        for (String role:
                roles) {

            roleSet.add(new Role(role));
        }

        roleRepository.saveAll(roleSet);
        user.setRoles(roleSet);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }


}

package ru.greatstep.spring.boot_security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.RoleRepository;
import ru.greatstep.spring.boot_security.repositories.UserRepository;
import ru.greatstep.spring.boot_security.service.UserService;


import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final RoleRepository roleRepository;


    private final UserService userService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RestController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/rest/principal")
    public User getPrincipalInfo(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        return user;
    }

    @PostMapping("/rest")
    public User addNewUser(@RequestBody User user) {
        roleRepository.saveAll(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        return user;
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        roleRepository.saveAll(user.getRoles());
        if (user.getPassword() == null ||
                user.getPassword().equals("") || user.getPassword().equals(userService.findUserById((long) id).getPassword())) {
            user.setPassword(userService.findUserById((long) id).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userService.saveAndFlush(user);
        return user;
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }

}

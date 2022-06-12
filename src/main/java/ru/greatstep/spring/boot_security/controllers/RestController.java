package ru.greatstep.spring.boot_security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.RoleRepository;
import ru.greatstep.spring.boot_security.repositories.UserRepository;


import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final RoleRepository roleRepository;

    final UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RestController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @GetMapping("/rest/principal")
    public User getPrincipalInfo(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        User user = userRepository.findUserById(id);
        return user;
    }

    @PostMapping("/rest")
    public User addNewUser(@RequestBody User user) {
        roleRepository.saveAll(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        roleRepository.saveAll(user.getRoles());
        if (user.getPassword() == null ||
                user.getPassword().equals("") || user.getPassword().equals(userRepository.findUserById((long) id).getPassword())) {
            user.setPassword(userRepository.findUserById((long) id).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userRepository.saveAndFlush(user);
        return user;
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

}

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
    public String snowUserList(Model model,Principal principal) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("activeUser",user);
        model.addAttribute("newUser", new User());
        model.addAttribute("user",user);


        return "admin";
    }

//    @GetMapping("/")
//    public void showUserInfo(Model model,Principal principal){
//        User user = userService.findByUsername(principal.getName());
//
//
//    }

    @GetMapping("/new")
    public String newUser(Model model,Principal principal) {

        model.addAttribute("newUser", new User());
        model.addAttribute("role", new ArrayList<Role>());
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("activeUser",user);

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

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable("id") int id,Principal principal) {
        model.addAttribute("user", userRepository.findUserById((long) id));
        User user = userService.findByUsername(principal.getName());

        model.addAttribute("activeUser",user);
        return "admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,  @RequestParam(value = "role") String[] roles,
                         Model model,@PathVariable("id") int id) {


        model.addAttribute("user",userRepository.findUserById((long)id));
        ArrayList<Role> roleSet = userService.getRoleCollectionToStringArray(roles);
        roleRepository.saveAll(roleSet);
        if(user.getPassword().equals("")){
            user.setPassword(userRepository.findUserById(user.getId()).getPassword());
        } else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        user.setRoles(roleSet);

        userRepository.saveAndFlush(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }


}

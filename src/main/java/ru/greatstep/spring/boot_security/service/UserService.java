package ru.greatstep.spring.boot_security.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greatstep.spring.boot_security.models.Role;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.RoleRepository;
import ru.greatstep.spring.boot_security.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        Hibernate.initialize(user.getAuthorities());


        return user;
    }

    public ArrayList<Role> getRoleCollectionToStringArray(String[] roles) {
        ArrayList<Role> roleArray = new ArrayList<>();
        for (String role :
                roles) {

            roleArray.add(new Role(role));
        }
        return roleArray;
    }

    public void addDefaultAdmin(){
        User admin = new User("Dmitriy","Tkachenko");
        admin.setUsername("admin@mail.ru");
        admin.setPassword("$2a$12$SOnZ9kd8ptoQbrTc6whqU.t/gtkmlJe3fNeWE6htnNmNgberD8I4S"); // admin
        admin.setAge(29);
        List<Role> rolesList = new ArrayList<>();

        Role roleOne = new Role();
        roleOne.setName("ROLE_ADMIN");
        roleRepository.save(roleOne);
        rolesList.add(roleOne);

        Role roleTwo = new Role();
        roleTwo.setName("ROLE_USER");
        roleRepository.save(roleTwo);
        rolesList.add(roleTwo);


        admin.setRoles(rolesList);

        userRepository.save(admin);

    }


}

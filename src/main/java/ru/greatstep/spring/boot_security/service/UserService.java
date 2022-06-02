package ru.greatstep.spring.boot_security.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
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

}

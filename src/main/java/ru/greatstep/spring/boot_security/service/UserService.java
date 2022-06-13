package ru.greatstep.spring.boot_security.service;

import ru.greatstep.spring.boot_security.models.Role;
import ru.greatstep.spring.boot_security.models.User;

import java.util.Collection;
import java.util.List;

public interface UserService {


    List<User> findAll();

    User findByUsername(String username);

    User findUserById(long id);

    void save(User user);

    void saveAndFlush(User user);

    void deleteById(long id);


}

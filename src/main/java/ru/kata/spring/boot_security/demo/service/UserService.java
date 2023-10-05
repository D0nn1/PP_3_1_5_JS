package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    List<Role> getAllRoles();

    void saveUser(User user);

    void saveUser(User user, List<String> roles);

    User getUserById(int id);

    User getUserByName(String name);

    void deleteUser(int id);

    void updateUser(User user);

    void updateUser(User user, List<String> roles);

    void removeRole(int userId, String roleName);

    void removeRole(String roleName);

    Set<Role> convertStringToRole(List<String> stringList);

    Role convertStringToRole(String string);

    User setAndEncodePassword(User user, PasswordEncoder passwordEncoder);
}

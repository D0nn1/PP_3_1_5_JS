package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddingNewUsersAtStartup implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AddingNewUsersAtStartup(UserService userService,
                                   PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        final String roleAdmin = "ROLE_ADMIN";
        final String roleUser = "ROLE_USER";

        List<String> firstUserRoles = new ArrayList<>() {{
//            add(roleUser);
            add(roleAdmin);
        }};
        List<String> secondUserRoles = new ArrayList<>() {{
            add(roleUser);
        }};

        User donni = new User();
        donni.setUsername("donni");
        donni.setName("Roman");
        donni.setSurname("Tynchirov");
        donni.setAge(20);
        donni.setPassword(passwordEncoder.encode(("100")));

        User marlovo = new User();
        marlovo.setUsername("marlovo");
        marlovo.setName("Юрий");
        marlovo.setSurname("Катков");
        marlovo.setAge(0);
        marlovo.setPassword(passwordEncoder.encode(("100")));

        User defaultUser = new User();
        defaultUser.setUsername("user");
        defaultUser.setName("Default");
        defaultUser.setSurname("User");
        defaultUser.setAge(45);
        defaultUser.setPassword(passwordEncoder.encode(("100")));


        userService.saveUser(donni, firstUserRoles);
        userService.saveUser(marlovo, firstUserRoles);
        userService.saveUser(defaultUser, secondUserRoles);


    }
}

package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/showAccount")
    public ResponseEntity<User> showInfoUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserByName((principal.getName())));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(Math.toIntExact(id)).getRoles(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(Math.toIntExact(id));
        if (user == null) {
            throw new RuntimeException("Пользователя с ID = " + id + " нет в БД");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody @Valid User newUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder info_about_errors = new StringBuilder();
            List<FieldError> fields_of_errors = bindingResult.getFieldErrors();
            fields_of_errors.forEach(error ->
                    info_about_errors.append(error.getField())
                            .append(" - ")
                            .append(error.getDefaultMessage())
                            .append(";"));
            throw new RuntimeException(info_about_errors.toString());
        }

        userService.setAndEncodePassword(newUser, passwordEncoder);
        userService.saveUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User userFromWebPage, @PathVariable("id") Long id) {
        userFromWebPage.setId(Math.toIntExact(id));
        userService.setAndEncodePassword(userFromWebPage, passwordEncoder);
        userService.updateUser(userFromWebPage);

        return new ResponseEntity<>(userFromWebPage, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(Math.toIntExact(id));
        if (user == null) {
            throw new RuntimeException("Пользователь с id = " + id + " не найден в БД и не может быть удален");
        }
        userService.deleteUser(Math.toIntExact(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
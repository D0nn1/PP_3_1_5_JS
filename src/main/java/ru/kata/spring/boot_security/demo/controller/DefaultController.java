package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/admin/info")
    public String showAllUsers() {
        return "admin-page";
    }

    @GetMapping("/user/info")
    public String showUserInfo() {
        return "user-page";
    }
}
package com.marko.kladionicajava.controller;

import com.marko.kladionicajava.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping()
    public String showHome() {
        return "fancy-register";
    }

    @PostMapping("/save-user")
    public String saveUser(@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword,
                                        @RequestParam("username") String username, @RequestParam("email") String email) {
        userService.writeUser(username, password, confirmPassword, email);

        return "fancy-login";
    }



}


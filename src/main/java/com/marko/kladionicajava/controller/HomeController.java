package com.marko.kladionicajava.controller;

import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import java.security.Principal;

@Controller
@SessionAttributes("user")
@RequiredArgsConstructor

public class HomeController {
    private final UserService userService;



    @GetMapping("/")
    public String showHome1(Model model, Principal principal, HttpSession session) {
        Users user = userService.getUser(principal);
        session.setAttribute("userCurrent", user);
        model.addAttribute("user", user);
        return "home";
    }
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(Model theModel){return "fancy-login";}

    @GetMapping("/access-denied")
    public String accessDenied(Model theModel){return "access-denied";}


}
//krondyom
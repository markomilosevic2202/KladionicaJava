package com.marko.kladionicajava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(Model theModel){

        return "fancy-login";
    }
}

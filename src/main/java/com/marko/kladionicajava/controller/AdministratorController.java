package com.marko.kladionicajava.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
@RequiredArgsConstructor
public class AdministratorController {

    @GetMapping()
    public String showMyLoginPage(Model theModel){

        return "settings";
    }


}

package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.FileService;
import com.marko.kladionicajava.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final UserService userService;
    @GetMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        try {
            fileService.uploadImage(file, "email");
            model.addAttribute("popupMessage", "Fajl uspešno otpremljen.");
        } catch (Exception e) {
            model.addAttribute("popupMessage", "Došlo je do greške prilikom otpremanja fajla: " + e.getMessage());
        }
        return "home";
    }


    @GetMapping("/show")
    public String showImage(Model model) {
        model.addAttribute("imageUrl",fileService.retrieveImage("9c99e298-1ed4-40c9-abfe-dfe8c10ae5bc.jpg"));
        return "show-image";
        }

    @GetMapping("/show-edit-profile")
    public String showEditProfile(Model model, Principal principal) {
        Users user = userService.getUser(principal);
        model.addAttribute("user", user);
        return "file";
    }
}

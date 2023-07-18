package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.repository.EmailRepository;
import com.marko.kladionicajava.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final EmailService emailService;
    @GetMapping()
    public String showSettings(Model model) {
        List<Email> listReports = emailService.getEmails();
        model.addAttribute("emails", listReports);
        return "settings";

    }

    @GetMapping("/add-email")
    public String showAddEmail() {

        return "add-email";
    }

    @GetMapping("/save")
    public String email(@ModelAttribute("tempEmail") Email email, Principal principal) {
        emailService.createEmail(email);
        return "redirect:/settings";
    }
    @GetMapping("/delete")
    public String deleteReports(@RequestParam("emailId") String emailId) {

        emailService.deleteEmail(emailId);
        return "redirect:/settings";
    }
}

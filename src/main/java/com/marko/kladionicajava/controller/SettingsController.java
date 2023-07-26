package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final EmailService emailService;
    private final AppConfigService appConfig;



    @GetMapping()
    public String showSettings(Model model) {
        List<Email> listReports = emailService.getEmails();
        String timeReview = appConfig.getTimeReview();
        model.addAttribute("emails", listReports);
        model.addAttribute("timeReviewModel", timeReview);
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

    @PostMapping("/saveTimeReview")
    public String saveTimeReview(@RequestParam("timeReview") String timeReview) {
       appConfig.setTimeReview(timeReview);

        return "redirect:/settings";
    }
}

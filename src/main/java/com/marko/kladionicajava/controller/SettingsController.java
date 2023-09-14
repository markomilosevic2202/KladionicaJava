package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Settings;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;

import com.marko.kladionicajava.tools.JsonService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
@Getter
@Setter
@ToString

public class SettingsController {

    private final EmailService emailService;
    private final AppConfigService appConfig;
    private final JsonService jsonService;
    public Settings settings;

    @PostConstruct
    public void initialize() {
        settings = jsonService.readJsonFileSettings();
        System.out.println();
    }

    @GetMapping()
    public String showSettings(Model model, HttpSession session) {
        List<Email> listReports = emailService.getEmails();
        settings = jsonService.readJsonFileSettings();
        model.addAttribute("emails", listReports);
        model.addAttribute("settings", settings);
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
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

    @PostMapping("/save-time-review")
    public String saveTimeReview(@RequestParam("timeReview") String timeReview) {
        settings.setTimeReviewMozzart(timeReview);
        jsonService.writeJsonFileSettings(settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-time-refresh-matchs")
    public String saveTimeRefreshMatchs(@RequestParam("timeMatchRefresh") String timeMatchRefresh) {
        settings.setTimeRefreshMatches(Integer.valueOf(timeMatchRefresh));
        jsonService.writeJsonFileSettings(settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-time-refresh-quotas")
    public String saveTimeRefreshQuotas(@RequestParam("timeQuotaRefresh") String timeQuotasRefresh) {
        settings.setTimeRefreshQuotas(Integer.parseInt(timeQuotasRefresh));
        jsonService.writeJsonFileSettings(settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-filters")
    public String saveFilters(@RequestParam("minimumQuota") String minimumQuota, @RequestParam("minimumProfit") String minimumProfit,
                              @RequestParam("minimumPayment") String minimumPayment) {
        settings.setMinimumQuota(Float.parseFloat(minimumQuota));
        settings.setMinimumProfit(Float.parseFloat(minimumProfit));
        settings.setMinimumPayment(Float.parseFloat(minimumPayment));
        jsonService.writeJsonFileSettings(settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-stake-for-calculation")
    public String saveStakeForCalculation(@RequestParam("stakeForCalculation") String stakeForCalculation) {
        settings.setStakeForCalculation(Float.parseFloat(stakeForCalculation));
        jsonService.writeJsonFileSettings(settings);
        return "redirect:/settings";
    }
}

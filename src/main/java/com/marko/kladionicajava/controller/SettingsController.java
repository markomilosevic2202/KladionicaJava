package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Settings;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;

import com.marko.kladionicajava.service.SettingsService;
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


public class SettingsController {

    private final EmailService emailService;
    private final SettingsService settingsService;
    private final JsonService jsonService;


    @PostConstruct
    public void initialize() {
        settingsService.readJsonSettings();
    }

    @GetMapping()
    public String showSettings(Model model, HttpSession session) {
        List<Email> listReports = emailService.getEmails();
        model.addAttribute("emails", listReports);
        model.addAttribute("settings", settingsService.getSettings());
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
        settingsService.settings.setTimeReviewMozzart(timeReview);
        jsonService.writeJsonFileSettings(settingsService.settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-time-refresh-matchs")
    public String saveTimeRefreshMatchs(@RequestParam("timeMatchRefresh") String timeMatchRefresh) {
        settingsService.settings.setTimeRefreshMatches(Integer.valueOf(timeMatchRefresh));
        jsonService.writeJsonFileSettings(settingsService.settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-time-refresh-quotas")
    public String saveTimeRefreshQuotas(@RequestParam("timeQuotaRefresh") String timeQuotasRefresh) {
        settingsService.settings.setTimeRefreshQuotas(Integer.parseInt(timeQuotasRefresh));
        jsonService.writeJsonFileSettings(settingsService.settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-filters")
    public String saveFilters(@RequestParam("minimumQuota") String minimumQuota, @RequestParam("minimumProfit") String minimumProfit,
                              @RequestParam("minimumPayment") String minimumPayment) {
        settingsService.settings.setMinimumQuota(Float.parseFloat(minimumQuota));
        settingsService.settings.setMinimumProfit(Float.parseFloat(minimumProfit));
        settingsService.settings.setMinimumPayment(Float.parseFloat(minimumPayment));
        jsonService.writeJsonFileSettings(settingsService.settings);
        return "redirect:/settings";
    }

    @PostMapping("/save-stake-for-calculation")
    public String saveStakeForCalculation(@RequestParam("stakeForCalculation") String stakeForCalculation) {
        settingsService.settings.setStakeForCalculation(Float.parseFloat(stakeForCalculation));
        jsonService.writeJsonFileSettings(settingsService.settings);
        return "redirect:/settings";
    }
}

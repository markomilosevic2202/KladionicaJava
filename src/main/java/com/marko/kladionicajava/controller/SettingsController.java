package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;

import jakarta.servlet.http.HttpSession;
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
    public String showSettings(Model model, HttpSession session) {
        List<Email> listReports = emailService.getEmails();
        String timeReview = appConfig.getTimeReviewMaxbet();
        model.addAttribute("emails", listReports);
        model.addAttribute("timeReviewModel", timeReview);
        model.addAttribute("timeRefreshMatch", appConfig.getTimeRefreshMatches());
        model.addAttribute("timeRefreshQuotas", appConfig.getTimeRefreshQuotas());
        model.addAttribute("minimumQuota", appConfig.getMinimumQuota());
        model.addAttribute("minimumPayment", appConfig.getMinimumPayment());
        model.addAttribute("minimumProfit", appConfig.getMinimumProfit());
        model.addAttribute("stakeForCalculation", appConfig.getStakeForCalculation());
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
       appConfig.setTimeReviewMaxbet(timeReview);

        return "redirect:/settings";
    }

    @PostMapping("/save-time-refresh-matchs")
    public String saveTimeRefreshMatchs(@RequestParam("timeMatchRefresh") String timeMatchRefresh) {
        appConfig.setTimeRefreshMatches(Integer.parseInt(timeMatchRefresh));

        return "redirect:/settings";
    }

    @PostMapping("/save-time-refresh-quotas")
    public String saveTimeRefreshQuotas(@RequestParam("timeQuotaRefresh") String timeQuotasRefresh) {
        appConfig.setTimeRefreshQuotas(Integer.parseInt(timeQuotasRefresh));

        return "redirect:/settings";
    }

    @PostMapping("/save-filters")
    public String saveFilters(@RequestParam("minimumQuota") String minimumQuota, @RequestParam("minimumProfit") String minimumProfit,
                              @RequestParam("minimumPayment") String minimumPayment) {
        appConfig.setMinimumQuota(Float.parseFloat(minimumQuota));
        appConfig.setMinimumProfit(Float.parseFloat(minimumProfit));
        appConfig.setMinimumPayment(Float.parseFloat(minimumPayment));

        return "redirect:/settings";
    }
    @PostMapping("/save-stake-for-calculation")
    public String saveStakeForCalculation(@RequestParam("stakeForCalculation") String stakeForCalculation) {
       appConfig.setStakeForCalculation(Float.parseFloat(stakeForCalculation));

       return "redirect:/settings";
    }
}

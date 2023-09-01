package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;
import com.marko.kladionicajava.service.MatchService;
import com.marko.kladionicajava.service.QuotasService;
import com.marko.kladionicajava.tools.SortService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final QuotasService quotasService;
    private final SortService sortService;


    @GetMapping()
    public String showMatches(Model model) {
        List<Date> optionalView = matchService.getOptionalView();
        if (optionalView.isEmpty()) {
            Date currentDate = new Date();
            optionalView.add(currentDate);
        }
        Date timeView = optionalView.get(0);
        List<Quotas> listMatch = quotasService.getAllQuotasLastView(timeView);
        sortService.sortQuota(listMatch);
        model.addAttribute("optionalViews", optionalView);
        model.addAttribute("quotas", listMatch);

        return "matches";
    }

    @RequestMapping("/show-set-time")
    public String handleFormSubmission(@RequestParam("myDropdown") String selectedValue, Model model){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        List<Quotas> listMatch = null;
        try {
            listMatch = quotasService.getAllQuotasLastView(sdf.parse(selectedValue));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        sortService.sortQuota(listMatch);
        model.addAttribute("quotas", listMatch);
        List<Date> optionalView = matchService.getOptionalView();
        model.addAttribute("optionalViews", optionalView);
        model.addAttribute("views", selectedValue);
        return "matches";
    }
    //@RequestParam("myDropdown") String selectedValue,

    @GetMapping("/refresh-match")
   // @Scheduled(fixedRateString = "#{appConfigService.getTimeRefreshMatches * 60000}")
    public String refreshMatches() {
        System.out.println("////////////////////////////// Refresh Match ////////////////////////////////// ");
        matchService.refreshShow();
        return "redirect:/matches";
    }


    @GetMapping("/refresh-quota")
    @Scheduled(fixedRateString = "#{appConfigService.getTimeRefreshQuotas * 60000}")
    public String refreshQuota() {
        System.out.println("////////////////////////////// Refresh Quotas ////////////////////////////////// ");
        quotasService.refreshQuotas();
        return "redirect:/matches";
    }

    @GetMapping("/individual-display-match")
    public String individualDisplayMatch(@RequestParam("matchId") String matchId, Model model) {

        Match match = matchService.getMatch(matchId);
        model.addAttribute("match", match);
        return "matchIndividual";
    }

    @GetMapping("/open-league")
    public String openLeague(@RequestParam("league") String league, Model model) {

        matchService.openLeague(league);
        return "redirect:/matches";
    }

}
//
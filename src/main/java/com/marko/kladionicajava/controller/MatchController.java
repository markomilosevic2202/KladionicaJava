package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;
import com.marko.kladionicajava.service.MatchService;
import com.marko.kladionicajava.service.QuotasService;
import com.marko.kladionicajava.tools.SortService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showMatches(Model model){
        List<String> optionalView = matchService.getOptionalView();
        if(optionalView.isEmpty()){
            optionalView.add(" - ");
        }
        String timeView = optionalView.get(0);
        List<Quotas> listMatch = quotasService.getAllQuotasLastView(timeView);
        sortService.sortQuota(listMatch);
        model.addAttribute("optionalViews", optionalView);
        model.addAttribute("quotas", listMatch);

        return "matches";
    }

    @RequestMapping ("/show-set-time")
    public String handleFormSubmission(@RequestParam("myDropdown") String selectedValue, Model model) {
        List<Quotas> listMatch = quotasService.getAllQuotasLastView(selectedValue);
        sortService.sortQuota(listMatch);
        model.addAttribute("quotas", listMatch);
        List<String> optionalView = matchService.getOptionalView();
        model.addAttribute("optionalViews", optionalView);
        model.addAttribute("views", selectedValue);
        return "matches";
    }

    @GetMapping("/refresh-match")
    @Scheduled(fixedRateString = "#{appConfigService.getTimeRefreshMatches * 60000}")
    public String refreshMatches(){
        matchService.refreshShow();
        return "redirect:/matches";
    }



    @GetMapping("/refresh-quota")
    @Scheduled(fixedRateString = "#{appConfigService.getTimeRefreshQuotas * 60000}")
    public String refreshQuota(){
        quotasService.refreshQuotas();

        return "redirect:/matches";
    }

    @GetMapping("/individual-display-match")
    public String individualDisplayMatch(@RequestParam("matchId") String matchId, Model model){

        Match match = matchService.getMatch(matchId);
        model.addAttribute("match", match);
        return "matchIndividual";
    }



}
//
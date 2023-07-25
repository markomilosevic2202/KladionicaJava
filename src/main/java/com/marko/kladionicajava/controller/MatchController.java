package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.service.EmailService;
import com.marko.kladionicajava.service.MatchService;
import com.marko.kladionicajava.service.QuotasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final QuotasService quotasService;

    @GetMapping()
    public String showMatches(Model model){
        List<String> optionalView = matchService.getOptionalView();
        String timeView = optionalView.get(0);
        List<Quotas> listMatch = quotasService.getAllQuotasLastView(timeView);
        model.addAttribute("optionalViews", optionalView);
        model.addAttribute("quotas", listMatch);

        return "matches";
    }

    @RequestMapping ("/showSetTime")
    public String handleFormSubmission(@RequestParam("myDropdown") String selectedValue, Model model) {
        System.out.println(selectedValue);
        List<Quotas> listMatch = quotasService.getAllQuotasLastView(selectedValue);
        model.addAttribute("quotas", listMatch);
        List<String> optionalView = matchService.getOptionalView();
        model.addAttribute("optionalViews", optionalView);
        return "matches";
    }

    @GetMapping("/refreshMatch")
    public String refreshMatches(){
        matchService.refreshShow();
        return "redirect:/matches";
    }

    @GetMapping("/findPairInForeignBettingShop")
    public String findPairIn(){
        matchService.findPairInForeignBettingShop();

        return "redirect:/matches";
    }

    @GetMapping("/refreshQuota")
    public String refreshQuota(){
        matchService.refreshQuots();

        return "redirect:/matches";
    }

    @GetMapping("/individualDisplayMatch")
    public String individualDisplayMatch(@RequestParam("matchId") String matchId){

        Match match = matchService.getMatch(matchId);
        System.out.println(matchId);
        return "redirect:/matches";
    }

}

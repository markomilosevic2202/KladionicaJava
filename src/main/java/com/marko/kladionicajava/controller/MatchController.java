package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.service.EmailService;
import com.marko.kladionicajava.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping()
    public String showMatches(Model model){
        List<String> optionalView = matchService.getOptionalView();
        List<Match> listMatch = matchService.getMatches();
        model.addAttribute("matches", listMatch);
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

}

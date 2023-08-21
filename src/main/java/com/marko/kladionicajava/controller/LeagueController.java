package com.marko.kladionicajava.controller;

import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.League;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.service.LeagueService;
import com.marko.kladionicajava.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/league")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping()
    public String showLeague(Model model) {
        List<League> listLeague = leagueService.getAllLeague();
        model.addAttribute("leagues", listLeague);
        return "league";

    }

    @PostMapping("/updateReviewStatus")
    public String updateReviewStatus() {
        leagueService.updateLeague();
        return null;
    }

    @GetMapping("/deleteLeague")
    public String deleteLeague(@RequestParam("leagueId") String leagueId) {
        leagueService.deleteLeague(leagueId);
        return "redirect:/league";
    }

    @GetMapping("/add-league")
    public String showAddLeague() {

        return "add-league";
    }

    @GetMapping("/save")
    public String email(@ModelAttribute("tempLeague") League league) {
        leagueService.createLeague(league);
        return "redirect:/league";
    }
}



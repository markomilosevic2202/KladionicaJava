package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.League;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.LeagueService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/league")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping()
    public String showLeague(Model model, HttpSession session) {
        List<League> listLeague = leagueService.getAllLeague();
        model.addAttribute("leagues", listLeague);
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        return "league";

    }

    @PostMapping("/updateReviewStatus")
    public String updateReviewStatus(@RequestParam String leagueId, @RequestParam Boolean isChecked) {
        try {
            leagueService.updateReviewStatus(leagueId, isChecked);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return "redirect:/league";
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



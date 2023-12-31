package com.marko.kladionicajava.controller;



import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.MatchService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/withoutLink")
@RequiredArgsConstructor
public class WithoutLinkController {

    private final MatchService matchService;

    @GetMapping()
    public String showMatchesWithoutLink(Model model, HttpSession session) {
        List<Match> listMatch= matchService.findAllWithoutLink();
        model.addAttribute("clubNameWithoutForeignName", listMatch);
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        return "add-link";

    }

    @PostMapping("/updateLink")
    public String updateClub(@ModelAttribute("match") Match match) {

        matchService.updateLink(match);

        return "redirect:/withoutLink";
    }

    @GetMapping("/showAllMatches")
    public String showAllMatch(Model model, HttpSession session) {
        List<Match> listMatch= matchService.findAllMatches();
        model.addAttribute("clubNameWithoutForeignName", listMatch);
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        return "add-link";

    }
}

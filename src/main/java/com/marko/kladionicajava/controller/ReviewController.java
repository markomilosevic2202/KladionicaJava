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
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final MatchService matchService;

    @GetMapping()
    public String showMatchesWithoutLink(Model model, HttpSession session) {
        List<Match> listMatch= matchService.findAllHasLink();
        model.addAttribute("clubNameWithoutForeignName", listMatch);
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        return "review";

    }
    @PostMapping("/updateReviewStatus")
    public String updateClub(@ModelAttribute("match") Match match) {
        matchService.updateReview(match);
        return "redirect:/review";
    }
}

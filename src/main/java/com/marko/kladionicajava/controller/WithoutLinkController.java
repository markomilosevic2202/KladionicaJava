package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/withoutLink")
@RequiredArgsConstructor
public class WithoutLinkController {

    private final MatchService matchService;

    @GetMapping()
    public String showSettingsDefault(Model model, String nameBetShop) {
        List<Match> listMatch= matchService.findAllWithoutLink(nameBetShop);
        model.addAttribute("clubNameWithoutForeignName", listMatch);
        return "add-names";

    }
}

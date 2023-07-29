package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.service.ClubNameService;
import com.marko.kladionicajava.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/addNames")
@RequiredArgsConstructor
public class AddNamesController {

    private final ClubNameService clubNameService;
    private final MatchService matchService;

    @GetMapping()
    public String showSettings(Model model) {
        List<ClubName> listClubName = clubNameService.getAllWithoutForeignName();
        model.addAttribute("clubNameWithoutForeignName", listClubName);
        return "add-names";

    }
    @PostMapping("/updateClubName")
    public String updateClub(@ModelAttribute("club") ClubName clubName) {

        clubNameService.updateClubName(clubName);

        return "redirect:/addNames";
    }
    @GetMapping("/deleteClubName")
    public String deleteClub(@RequestParam("idClub") String idClub) {

        clubNameService.deleteClubName(idClub);

        return "redirect:/addNames";
    }
}

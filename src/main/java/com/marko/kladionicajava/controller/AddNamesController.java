package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.service.ClubNameService;
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

    @GetMapping()
    public String showSettings(Model model) {
        List<ClubName> listClubName = clubNameService.getAllWithoutForeignName();
        model.addAttribute("clubNameWithoutForeignName", listClubName);
        return "addNames";

    }
    @PostMapping("/updateClubName")
    public String updateClub(@ModelAttribute("club") ClubName clubName) {
        System.out.println(clubName.getMaxbetName());


        return "redirect:/addNames";
    }
}

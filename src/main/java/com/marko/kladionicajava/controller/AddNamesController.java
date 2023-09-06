package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.service.ClubNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/addNames")
@RequiredArgsConstructor
public class AddNamesController {

    private final ClubNameService clubNameService;


    @GetMapping("/setPreview")
    public String setPreview(@RequestParam("myDropdown1") String selectedValue, Model model) {
        List<ClubName> listClubName = new ArrayList<>();
        if (selectedValue.equals("maxbet")) {
            listClubName = clubNameService.getAllWithoutMaxbetName();
        } else if (selectedValue.equals("meridan")) {
            listClubName = clubNameService.getAllWithoutMeridianName();
        } else if (selectedValue.equals("mozzart")) {
            listClubName = clubNameService.getAllWithoutMeridianName();
        } else if (selectedValue.equals("foreign")) {
            listClubName = clubNameService.getAllWithoutForeignName();
        } else {
             listClubName = clubNameService.getAllClubName();
        }
        model.addAttribute("clubNameWithoutForeignName", listClubName);
        return "add-names";

    }

    @GetMapping()
    public String showSettingsDefault(Model model) {
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

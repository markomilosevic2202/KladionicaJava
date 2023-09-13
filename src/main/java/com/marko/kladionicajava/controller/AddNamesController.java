package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.service.ClubNameService;

import jakarta.servlet.http.HttpSession;
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
    public String setPreview(@RequestParam("myDropdown1") String selectedValue, Model model, HttpSession session) {
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
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        return "add-names";

    }

    @GetMapping()
    public String showSettingsDefault(Model model, HttpSession session) {
        List<ClubName> listClubName = clubNameService.getAllWithoutForeignName();
        model.addAttribute("clubNameWithoutForeignName", listClubName);
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        return "add-names";

    }

    @PostMapping("/updateClubName")
    public String updateClub(Model model, @ModelAttribute("club") ClubName clubName, HttpSession session) {
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        clubNameService.updateClubName(clubName);
        return "redirect:/addNames";
    }

    @GetMapping("/deleteClubName")
    public String deleteClub(@RequestParam("idClub") String idClub, Model model, HttpSession session) {
        model.addAttribute("user", (Users) session.getAttribute("userCurrent"));
        clubNameService.deleteClubName(idClub);

        return "redirect:/addNames";
    }
}

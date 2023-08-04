package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.repository.ClubNamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClubNameService {

    private final ClubNamesRepository clubNamesRepository;


    public List<ClubName> getAllWithoutForeignName() {

        return clubNamesRepository.findAllByForeignNameIsNull();
    }
    public List<ClubName> getAll() {

        return clubNamesRepository.findAll();
    }
    public List<ClubName> getAllWithoutMaxbetName() {

        return clubNamesRepository.findAllByMaxbetNameIsNull();
    }

    public List<ClubName> getAllWithoutMeridianName() {

        return clubNamesRepository.findAllByMeridianNameIsNull();
    }

    public List<ClubName> getAllWithoutMozzartName() {

        return clubNamesRepository.findAllByMozzartNameIsNull();
    }

    public void updateClubName(ClubName clubName) {

        try {
            Optional<ClubName> optionalClubName = clubNamesRepository.findById(clubName.getId());
            clubNamesRepository.save(setClubName(clubName,optionalClubName.get()));
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
    private ClubName setClubName(ClubName clubName, ClubName clubNameBase){
        if (clubName == null || clubNameBase == null) {
            return clubNameBase;
        }
        String foreignName = clubName.getForeignName().trim();
        if (foreignName != null && !foreignName.isEmpty() && !foreignName.equals(clubNameBase.getForeignName())) {
            clubNameBase.setForeignName(foreignName);
        }
        String maxbetName = clubName.getMaxbetName().trim();
        if (maxbetName != null && !maxbetName.isEmpty() && !maxbetName.equals(clubNameBase.getMaxbetName())) {
            clubNameBase.setMaxbetName(maxbetName);
        }
        String meridianName = clubName.getMeridianName().trim();
        if (meridianName != null && !meridianName.isEmpty() && !meridianName.equals(clubNameBase.getMeridianName())) {
            clubNameBase.setMeridianName(meridianName);
        }
        String mozzartName = clubName.getMozzartName().trim();
        if (mozzartName != null && !mozzartName.isEmpty() && !mozzartName.equals(clubNameBase.getMozzartName())) {
            clubNameBase.setMozzartName(mozzartName);
        }


        return clubNameBase;
    }

    public void deleteClubName(String idClub) {
        try {
            clubNamesRepository.deleteById(idClub);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ClubName> getAllClubName() {
         return clubNamesRepository.findAll();
    }
}

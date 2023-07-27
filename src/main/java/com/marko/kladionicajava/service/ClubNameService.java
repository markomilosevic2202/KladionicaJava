package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.repository.ClubNamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClubNameService {
    private final ClubNamesRepository clubNamesRepository;
    public List<ClubName> getAllWithoutForeignName() {

        return clubNamesRepository.findAllByForeignNameIsNull();
    }
}

package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.repository.QuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotasService {

    private final QuotaRepository quotaRepository;

    public List<Quotas> getAllQuotasLastView(String timeView) {

        try {
            return quotaRepository.findAllByNumber(timeView);

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

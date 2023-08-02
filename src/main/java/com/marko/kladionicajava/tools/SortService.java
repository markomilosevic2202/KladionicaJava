package com.marko.kladionicajava.tools;


import com.marko.kladionicajava.entitiy.Quotas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SortService {

    public List<Quotas> sortQuota(List<Quotas> listQuotas) {
        Collections.sort(listQuotas, new Comparator<Quotas>() {
            @Override
            public int compare(Quotas quotas1, Quotas quotas2) {

                double maxProfit1 = Math.max(quotas1.getProfitOne(), Math.max(quotas1.getProfitTwo(), quotas1.getProfitX()));
                double maxProfit2 = Math.max(quotas2.getProfitOne(), Math.max(quotas2.getProfitTwo(), quotas2.getProfitX()));
                return Double.compare(maxProfit2, maxProfit1);

            }});
        return listQuotas;
    }
}
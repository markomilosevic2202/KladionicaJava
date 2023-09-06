package com.marko.kladionicajava.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;



@Controller
@RequiredArgsConstructor
public class TimeController {

    private final MatchController quotasService;

//    @Scheduled(fixedRateString = "#{appConfigService.getTimeRefreshQuotas * 60000}")
//    private void scheduleRefreshQuotas() {
//        quotasService.refreshQuota();
//    }

//    @Scheduled(fixedRateString = "#{appConfigService.getTimeRefreshMatches * 60000}")
//    private void scheduleRefreshMatches() {
//        quotasService.refreshMatches();
//    }
}

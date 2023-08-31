package com.marko.kladionicajava.service;



import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@ConfigurationProperties("app")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AppConfigService {

    private String timeReviewMaxbet;
    private String addressMaxBet;
    private String addressForeign;
    private Float bet;
    @Value("${app.timeRefreshMatches}")
    private Integer timeRefreshMatches;
    @Value("${app.timeRefreshQuotas}")
    private Integer timeRefreshQuotas;
    private String addressMozzart;
    private String timeReviewMozzart;
    private Float minimumQuota;
    private Float minimumPayment;
    private Float minimumProfit;
    private String host;
    private String username;
    private String password;


}


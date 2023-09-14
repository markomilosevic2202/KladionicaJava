package com.marko.kladionicajava.entitiy;

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
public class Settings {

    @Value("${app.timeRefreshMatches}")
    private Integer timeRefreshMatches;
    @Value("${app.timeRefreshQuotas}")
    private Integer timeRefreshQuotas;
    private Integer timeReviewMaxbet;
    private Float stakeForCalculation;
    private String timeReviewMozzart;
    private Float minimumQuota;
    private Float minimumPayment;
    private Float minimumProfit;


}

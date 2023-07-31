package com.marko.kladionicajava.service;



import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AppConfigService {

    private String timeReview;
    private String addressMaxBet;
    private String addressForeign;
    private Float bet;
    @Value("${app.timeRefreshMatches}")
    private Integer timeRefreshMatches;
    @Value("${app.timeRefreshQuotas}")
    private Integer timeRefreshQuotas;

}


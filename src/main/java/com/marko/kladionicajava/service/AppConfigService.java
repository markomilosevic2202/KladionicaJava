package com.marko.kladionicajava.service;



import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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


    private String addressMaxBet;
    private String addressForeign;
    private String addressMozzart;
    private String host;
    private String username;
    private String password;


}


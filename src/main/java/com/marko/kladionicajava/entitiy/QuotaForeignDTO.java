package com.marko.kladionicajava.entitiy;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QuotaForeignDTO {

    private Float oneXQuota;
    private Float oneXBet;
    private Float twoXQuota;
    private Float twoXBet;
    private Float oneTwoQuota;
    private Float oneTwoBet;
}

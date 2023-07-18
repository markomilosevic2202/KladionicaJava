package com.marko.kladionicajava.entitiy;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MatchDTO {

    private String code;
    private String name;
    private String time;
    private String odds_one;
    private String odds_two;
    private String odds_x;

}

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
    private String one;
    private String name;
    private String time;

}

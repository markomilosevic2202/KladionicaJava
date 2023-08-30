package com.marko.kladionicajava.entitiy;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MatchDTO {

    private String code;
    private String name;
    private String league;
    private Date time;


}

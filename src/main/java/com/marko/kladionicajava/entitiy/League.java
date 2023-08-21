package com.marko.kladionicajava.entitiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "league")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class League {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name_league")
    private String nameLeague;
    @Column(name = "review")
    private Boolean review;
}

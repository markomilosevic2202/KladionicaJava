package com.marko.kladionicajava.entitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "odds")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matches_id")
    @JsonBackReference
    private Matches matches;
    @Column(name = "odds_one")
    private Float odds_one;
    @Column(name = "odds_two")
    private Float odds_two;
    @Column(name = "odds_x")
    private Float odds_x;
    @Column(name = "difference_one")
    private Float difference_one;
    @Column(name = "difference_one_two")
    private Float difference_two;
    @Column(name = "difference_two")
    private Float difference_x;
    @Column(name = "bet")
    private Float bet;


}

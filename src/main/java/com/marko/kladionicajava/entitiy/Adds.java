package com.marko.kladionicajava.entitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "quotas")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Adds {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "number_of_view", nullable = false)
    private int numberOfView;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matches_id")
    @JsonBackReference
    private Match matches;
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
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;



    @PrePersist
    private void prePersist() {
        Date date = new Date();
        this.createdAt = date;
        this.updatedAt = date;
    }


    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }


}

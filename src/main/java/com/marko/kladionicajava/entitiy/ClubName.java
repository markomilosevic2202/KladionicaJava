package com.marko.kladionicajava.entitiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "club_names")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ClubName {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "foreign_name")
    private String foreignName;
    @Column(name = "maxbet_name")
    private String maxbetName;
    @Column(name = "meridian_name")
    private String meridianName;
    @Column(name = "mozzart_name")
    private String mozzartName;
    @Column(name = "match_name")
    private String matchName;

//    @OneToMany(mappedBy = "hostNameClub", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Match> hostedMatches;
//
//    @OneToMany(mappedBy = "guestNameClub", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Match> guestMatches;
//    @Column(nullable = false)
//    private Date createdAt;
//    @Column(nullable = false)
//    private Date updatedAt;
//
//    @PrePersist
//    private void prePersist() {
//        Date date = new Date();
//        this.createdAt = date;
//        this.updatedAt = date;
//    }
//
//
//    @PreUpdate
//    public void preUpdate() {
//        this.updatedAt = new Date();
//    }




}

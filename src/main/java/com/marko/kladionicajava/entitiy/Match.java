package com.marko.kladionicajava.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "matchs")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Match {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "id_match")
    private String idMatch;
    @Column(name = "betting_shop")
    @Enumerated
    private NameBetting bettingShop;
    @Column(name = "linkForeign")
    private String linkOrbit;
    @ManyToOne (fetch = FetchType.EAGER )
    @JoinColumn(name = "id_host_club", nullable = true)
    private ClubName hostNameClub;
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "id_guest_club", nullable = true)
    private ClubName guestNameClub;
    @Column(name = "odds_one")
    private Float odds_one;
    @Column(name = "odds_two")
    private Float odds_two;
    @Column(name = "odds_x")
    private Float odds_x;
    @OneToMany(mappedBy = "matches", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Quota> odds;
    @Column(nullable = false)
    @JsonIgnore
    private Date createdAt;
    @Column(nullable = false)
    @JsonIgnore
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

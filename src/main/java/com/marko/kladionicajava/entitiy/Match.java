package com.marko.kladionicajava.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
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
    @Column(name = "date_Match")
    private Date dateMatch;
    @Column(name = "betting_shop")
    @Enumerated
    private NameBetting bettingShop;
    @Column(name = "link_foreign")
    private String linkForeign;
    @Column(name = "name_home")
    private String nameHome;
    @ManyToOne (fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_host_club", nullable = true)
    private ClubName hostNameClub;
    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_guest_club", nullable = true)
    private ClubName guestNameClub;
    @OneToMany(mappedBy = "matches", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quotas> quotas;
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

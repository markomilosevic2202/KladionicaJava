package com.marko.kladionicajava.entitiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "foreignBet")
    private String foreignName;
    @Column(name = "maxbet")
    private String maxbetName;
    @Column(name = "meridian")
    private String meridianName;
    @Column(name = "mozzart")
    private String mozzartName;


}

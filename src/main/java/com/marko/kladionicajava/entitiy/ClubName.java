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
    @Column(name = "foreign_name")
    private String foreignName;
    @Column(name = "maxbet_name")
    private String maxbetName;
    @Column(name = "meridian_name")
    private String meridianName;
    @Column(name = "mozzart_name")
    private String mozzartName;


}

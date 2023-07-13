package com.marko.kladionicajava.entitiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "emails")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Email {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "email")
    private String email;
    //todo add user who add email
}

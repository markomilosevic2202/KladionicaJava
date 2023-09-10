package com.marko.kladionicajava.entitiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Users {

    @Id
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "email")
    private String email;
    @Column(name = "image")
    private String image;
//    @Column(nullable = false)
//    private Date createdAt;
//    @Column(nullable = false)
//    private Date updatedAt;


//    @PrePersist
//    private void prePersist() {
//        Date date = new Date();
//        this.createdAt = date;
//        this.updatedAt = date;
//    }

}

package com.marko.kladionicajava.entitiy;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Column(name = "password", nullable = false)
    private String password;
//    @Column(name = "pw")
//    private String password;
    @Column(name = "active")
    private Boolean active;
//    @Column(name = "email")
//    private String email;
    @Column(name = "image")
    private String image;
}

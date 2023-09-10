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
@Table(name = "members")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Members {
    @Id
    @Column(name = "user_id", nullable = false)
    private String user_id;
    @Column(name = "pw", nullable = false)
    private String pw;
//    @Column(name = "pw")
//    private String password;
    @Column(name = "active")
    private Boolean active;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "image")
//    private String image;
}

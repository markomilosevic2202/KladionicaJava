package com.marko.kladionicajava.entitiy;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Roles {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    @JsonBackReference
    private Users username;
    @Id
    @Column(name = "role")
    private String role;
}

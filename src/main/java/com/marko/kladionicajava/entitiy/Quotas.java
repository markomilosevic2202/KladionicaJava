package com.marko.kladionicajava.entitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "quotas")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Quotas {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "time_view", nullable = false)
    private String timeView;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matches_id")
    @JsonBackReference
    private Match matches;
    @Column(name = "quota_one")
    private Float quotaOne;
    @Column(name = "quota_two")
    private Float quotaTwo;
    @Column(name = "quota_x")
    private Float quotaX;
    @Column(name = "difference_one")
    private Float differenceOne;
    @Column(name = "difference_two")
    private Float differenceTwo;
    @Column(name = "difference_x")
    private Float differenceX;
    @Column(name = "bet_one")
    private Float betOne;
    @Column(name = "bet_two")
    private Float betTwo;
    @Column(name = "bet_x")
    private Float betX;
    @Column(name = "profit_one")
    private Float profitOne;
    @Column(name = "profit_two")
    private Float profitTwo;
    @Column(name = "profit_x")
    private Float profitX;
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

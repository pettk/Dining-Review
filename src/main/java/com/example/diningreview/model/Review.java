package com.example.diningreview.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Setter
@Table(name="REVIEW")
public class Review {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="REVIEWBY")
    private String reviewBy;
    @Column(name="RESID")
    private Long restaurantId;
    @Column(name="PEANUTSCORE")
    private Integer peanutScore;
    @Column(name="EGGSCORE")
    private Integer eggScore;
    @Column(name="DAIRYSCORE")
    private Integer dairyScore;
    @Column(name="COMMENT")
    private String comment;
    @Column(name="STATUS")
    private ReviewStatus status = ReviewStatus.PENDING;

}

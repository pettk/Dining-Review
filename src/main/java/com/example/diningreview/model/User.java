package com.example.diningreview.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter
@Setter
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="NAME")
    private String displayName;
    @Column(name="CITY")
    private String city;
    @Column(name="STATE")
    private String state;
    @Column(name="ZIPCODE")
    private String zipCode;
    @Column(name="PEANUT")
    private Boolean peanutInterest;
    @Column(name="EGG")
    private Boolean eggInterest;
    @Column(name="DAIRY")
    private Boolean dairyInterest;
}

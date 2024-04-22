package com.example.diningreview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;
    @Column(name="ZIPCODE")
    private String zipCode;
    @Column(name="PEANUT")
    private Integer peanutScore;
    @Column(name="EGG")
    private Integer eggScore;
    @Column(name="DAIRY")
    private Integer dairyScore;
    @Column(name="OVERALL")
    private Float overall;
}


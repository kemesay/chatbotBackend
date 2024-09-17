package com.DXvalley.chatbot.models;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tourist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long touristId;
    private String fullName;
    private String country;
    private String city;
    private String subCity;
    private String gender;
    private String firstVisitedDate;
    private String touristType;
    private List<String> tourCategory;
    private String birthDate;
    private String email;
    private String phoneNum;
    private String passportId;
    private String zipcode;
    private  String updatedAt;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    public Tourist(String fullName, List<String> tourCategory, String firstVisitedDate, String touristType, String country, String city, String subCity, String gender, String birthDate, String email,
                   String phoneNum, String passportId,String updatedAt, String zipcode) {
        this.fullName = fullName;
        this.country = country;
        this.city = city;
        this.updatedAt =updatedAt;
        this.subCity = subCity;
        this.firstVisitedDate = firstVisitedDate;
        this.gender = gender;
        this.touristType = touristType;
        this.tourCategory = tourCategory;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNum = phoneNum;
        this.passportId = passportId;
        this.zipcode = zipcode;
    }


}

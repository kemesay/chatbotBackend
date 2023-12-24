package com.DXvalley.chatbot.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Visit> visits;

    public Tourist(String fullName, List<String> tourCategory, String firstVisitedDate, String touristType, String country, String city, String subCity, String gender, String birthDate, String email,
                   String phoneNum, String passportId, String zipcode) {
        this.fullName = fullName;
        this.country = country;
        this.city = city;
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

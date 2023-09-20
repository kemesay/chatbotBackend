package com.DXvalley.chatbot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String country;
    private String city;
    private String subCity;
    private String woreda;
    private String houseNumber;

    public Address( String country, String city, String subCity, String woreda, String houseNumber){
        this.country =country;
        this.city = city;
        this.subCity= subCity;
        this.woreda= woreda;
        this.houseNumber=houseNumber;
    }
    public Address(String phoneNumber, String email, String city, String subCity, String woreda, String houseNumber) {
        this("Ethiopia", city, subCity, woreda, houseNumber );
    }
}


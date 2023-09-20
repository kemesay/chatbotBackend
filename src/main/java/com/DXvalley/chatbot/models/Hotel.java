package com.DXvalley.chatbot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String hotelName;
    private float hotelLatitude;
    private float hotelLongitude;
    private String hotelAddress;
    private String hotelDescription;
    public Hotel(String hotelName, String hotelAddress ,String hotelDescription, float hotelLatitude, float hotelLongitude){
        this.hotelName=hotelName;
        this.hotelAddress=hotelAddress;
        this.hotelDescription=hotelDescription;
        this.hotelLongitude=hotelLongitude;
        this.hotelLatitude=hotelLatitude;


    }

}

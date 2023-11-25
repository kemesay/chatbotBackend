package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
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
    private String name;
    private float latitude;
    private float longitude;
    private String address;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    private Destination destination;
    public Hotel(String name, String address ,String description, float latitude, float longitude){
        this.name=name;
        this.address=address;
        this.description=description;
        this.longitude=longitude;
        this.latitude=latitude;


    }

}

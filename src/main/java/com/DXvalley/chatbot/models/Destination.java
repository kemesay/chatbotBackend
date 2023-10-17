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
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;
    private String name;
    private String address;
    private float latitude;
    private float longitude;
    private String description;
    public Destination(String name, String address, float latitude, float longitude, String description){
        this.name=name;
        this.description=description;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;

    }

}

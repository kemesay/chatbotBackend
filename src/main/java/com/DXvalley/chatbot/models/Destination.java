package com.DXvalley.chatbot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private  String createAt;
    private  String updatedAt;
    private float latitude;
    private float longitude;
    private String description;
    public Destination(String name,String createAt,String updatedAt,  String address, float latitude, float longitude, String description){
        this.name=name;
        this.description=description;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.createAt=createAt;
        this.updatedAt=updatedAt;

    }

}

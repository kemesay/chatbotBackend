package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;
    private String address;
    private float latitude;
    private String name;
    private float longitude;
    private String description;
    private String createAt;
    private String updatedAt;


    @ManyToOne
    private Destination destination;

    public Office(String address,String createAt, String updatedAt, String description, float longitude,  float latitude, String name){
        this.address=address;
        this.description=description;
        this.name= name;
        this.latitude= latitude;
        this.longitude=longitude;
        this.createAt=createAt;
        this.updatedAt=updatedAt;

    }

}

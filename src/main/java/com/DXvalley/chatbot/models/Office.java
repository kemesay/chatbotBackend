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
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;
    private String address;
    private float latitude;
    private String name;
    private float longitude;
    private String description;
    public Office(String address, String description, float longitude,  float latitude, String name){
        this.address=address;
        this.description=description;
        this.name= name;
        this.latitude= latitude;
        this.longitude=longitude;

    }

}

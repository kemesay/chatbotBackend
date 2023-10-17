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
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;
    private String name;
    private String address;
    private float latitude;
    private float longitude;
    private String description;
    public Bank(String name, String address ,String description, float latitude, float longitude){
        this.name=name;
        this.longitude=longitude;
        this.description=description;
        this.latitude=latitude;
        this.address=address;


    }

}

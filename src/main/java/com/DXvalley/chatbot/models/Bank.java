package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Destination destination;


    public Bank(String name, String address, String description, float latitude, float longitude) {
        this.name = name;
        this.longitude = longitude;
        this.description = description;
        this.latitude = latitude;
        this.address = address;
    }

}

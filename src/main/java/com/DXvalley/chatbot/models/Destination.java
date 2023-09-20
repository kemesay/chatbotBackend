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
    private String destinationName;
    private float destinationLatitude;
    private float destinationLongitude;
    private String destinationDescription;
    public Destination(String destinationName, float destinationLatitude, float destinationLongitude, String destinationDescription){
        this.destinationName=destinationName;
        this.destinationDescription=destinationDescription;
        this.destinationLatitude=destinationLatitude;
        this.destinationLongitude=destinationLongitude;


    }

}

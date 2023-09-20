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
    private String officeAddress;
    private float officeLatitude;
    private String officeName;
    private float officeLongitude;
    private String description;
    public Office(String officeAddress, String description, float officeLongitude,  float officeLatitude, String officeName){
        this.officeAddress=officeAddress;
        this.description=description;
        this.officeName= officeName;
        this.officeLatitude= officeLatitude;
        this.officeLongitude=officeLongitude;

    }

}

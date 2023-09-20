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
    private String bankNamebranchName;
    private String bankAddress;

    private float branchLatitude;
    private float branchLongitude;
    private String bankDescription;
    public Bank(String bankNamebranchName, String bankAddress ,String bankDescription, float branchLatitude, float branchLongitude){
        this.bankNamebranchName=bankNamebranchName;
        this.bankDescription=bankDescription;
        this.branchLatitude=branchLatitude;
        this.branchLongitude=branchLongitude;
        this.bankAddress=bankAddress;


    }

}

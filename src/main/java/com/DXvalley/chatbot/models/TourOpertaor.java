package com.DXvalley.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TourOpertaor {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long tourOperatorId;
    private String tourOrgName;
    private String ownerFullName;
    private String ownerAddress;
    private String email;
    private String phoneNum;
    private String tourCategory;
    private String foundAt;
    private  String tinNum;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Destination> destinations = new ArrayList<>();
    public TourOpertaor(String tourOrgName,  String ownerFullName  ,String ownerAddress,  String email,
                                 String phoneNum, String foundAt,  String tourCategory, String tinNum){
        this.tourOrgName=tourOrgName;
        this.ownerFullName=ownerFullName;
        this.ownerAddress=ownerAddress;
        this.foundAt=foundAt;
        this.tourCategory=tourCategory;
        this.tinNum=tinNum;
        this.email=email;
        this.phoneNum= phoneNum;
    }

}








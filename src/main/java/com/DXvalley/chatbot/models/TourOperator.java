package com.DXvalley.chatbot.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import org.hibernate.mapping.Collection;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TourOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tourOperatorId;
    private String tourOrgName;
    private String ownerFullName;
    private String ownerAddress;
    private String email;
    private String phoneNum;
    private List<String> tourCategory;
    private List<String> touristType;
    private String foundAt;
    private String tinNum;
    private String createAt;
    private String updatedAt;
    // @OneToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "userId")
    // private Users users;

    @ManyToMany(fetch = FetchType.EAGER)

    private List<Destination> destinations = new ArrayList<>();


    // Additional methods...

    public TourOperator(String tourOrgName,String createAt,String updatedAt, String ownerFullName, String ownerAddress, String email, String phoneNum, List<String> touristType, String foundAt, List<String> tourCategory, String tinNum) {
        this.tourOrgName = tourOrgName;
        this.ownerFullName = ownerFullName;
        this.ownerAddress = ownerAddress;
        this.createAt=createAt;
        this.updatedAt =updatedAt;
        this.foundAt = foundAt;
        this.touristType = touristType;
        this.tourCategory = tourCategory;
        this.tinNum = tinNum;
        this.email = email;
        this.phoneNum = phoneNum;
    }

}








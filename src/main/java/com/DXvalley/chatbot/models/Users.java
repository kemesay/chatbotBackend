package com.DXvalley.chatbot.models;
import java.util.ArrayList;
import java.util.Collection;

import com.DXvalley.chatbot.auth_provider.Provider;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String phoneNum;
    private String fullName;
    private String email;
    private Boolean emailConfirmed;
    private String gender;
    private String birthDate;
    private String imageUrl;
    private String coverImgUrl;
    private String ip;
    private String createdAt;
    private String deletedAt;
    private Integer languageCode;
    private Integer accessFailedCount;
    private Boolean twoFactorEnabled;
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private Provider provider;



    //private String verificationCode;
    //private boolean verificationCodeCreatedAt;

    //user address
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    //user roles
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    public Users(String username, String phoneNum, String password, String fullName, String email, Boolean emailConfirmed,String gender,String birthDate,String imageUrl,String coverImgUrl,String ip,String createdAt,
                 String deletedAt,Integer languageCode,  Integer accessFailedCount,  Boolean twoFactorEnabled,Boolean isEnabled) {
        this.username = username;
        this.password = new BCryptPasswordEncoder(). encode(password);
        this.fullName = fullName;
        this.email= email;
        this.phoneNum=phoneNum;
        this.emailConfirmed= emailConfirmed;
        this.gender=gender;
        this.birthDate=birthDate;
        this.imageUrl=imageUrl;
        this.coverImgUrl=coverImgUrl;
        this.ip=ip;
        this.createdAt=createdAt;
        this.deletedAt=deletedAt;
        this.languageCode=languageCode;
        this.accessFailedCount=accessFailedCount;
        this.twoFactorEnabled=twoFactorEnabled;
        this.isEnabled=isEnabled;
        //this.verificationCode=verificationCode;
        // this.verificationCodeCreatedAt=verificationCodeCreatedAt;
    }


}

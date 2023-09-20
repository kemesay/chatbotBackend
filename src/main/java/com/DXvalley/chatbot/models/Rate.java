package com.DXvalley.chatbot.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long rateId;
    private String username;
    private Float rate;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "ratedAt")
    private LocalDateTime ratedAt = LocalDateTime.now();

}

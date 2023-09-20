package com.DXvalley.chatbot.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public  class RateAnalysis {
    private Float rateValue;
    private Integer counter = 0;
    private Float percent = 0.0F;
}


package com.DXvalley.chatbot.DTO;
import com.DXvalley.chatbot.models.Rate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RateDTO {
    private List<Rate> rate;
    private List<RateAnalysis> rateAnalysis;
}
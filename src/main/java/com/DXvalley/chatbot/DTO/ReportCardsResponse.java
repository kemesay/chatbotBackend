package com.DXvalley.chatbot.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportCardsResponse {
    private SingleReportCardData tourists;
    private SingleReportCardData packages;
    private SingleReportCardData destinations;
    private SingleReportCardData tourOperators;
    private SingleReportCardData employee;
}

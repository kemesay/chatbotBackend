package com.DXvalley.chatbot.controllers;

import com.DXvalley.chatbot.DTO.ReportCardsResponse;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.service.ReportService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/getCardData")
    ReportCardsResponse getReportCardsData() {
        return reportService.getCardsData();
    }
}
@Data
@RequiredArgsConstructor
class CardResponse {
    private String title;
    private Double data;
}
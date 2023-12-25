package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.TourOperator;
import com.DXvalley.chatbot.models.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TourOpService {
    ResponseEntity<?> registerTourOrg(Users user);

    List<TourOperator> fetchTourOperators();
    TourOperator editTourOp (TourOperator tourOperator);
}


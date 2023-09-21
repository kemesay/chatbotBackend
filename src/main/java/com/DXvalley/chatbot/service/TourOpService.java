package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Bank;
import com.DXvalley.chatbot.models.TourOpertaor;

import java.util.List;

public interface TourOpService {
    void registerTourOrg(TourOpertaor tourOpertaor);

    List<TourOpertaor> fetchTourOperators();
    TourOpertaor editTourOp (TourOpertaor tourOpertaor);
}


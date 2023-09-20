package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.DTO.RateAnalysis;
import com.DXvalley.chatbot.DTO.RateDTO;
import com.DXvalley.chatbot.models.Rate;
import com.DXvalley.chatbot.repository.RateRepository;
import com.DXvalley.chatbot.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class RateServiceImp implements  RateService{
    @Autowired
    private RateRepository rateRepository;
    @Override
    public void rate(Rate rate) {
        rateRepository.save(rate);
    }
    @Override
    public RateDTO fetchAllRates() {
        List<Rate> rates = rateRepository.findAll();
        RateDTO rateDTO = new RateDTO();
        List<RateAnalysis> listOfRateAnalysis = new ArrayList<RateAnalysis>();
        Float rateValues[] = {0.5F, 1.0F, 1.5F, 2.0F, 2.5F, 3.0F, 3.5F, 4.0F, 4.5F, 5.0F};

        for (Float r : rateValues) {
            RateAnalysis rateAnalysis = new RateAnalysis();
            rateAnalysis.setRateValue(r);
//
            for (Rate rate : rates) {
                if (rate.getRate().equals(r)) {
                    rateAnalysis.setCounter(rateAnalysis.getCounter() + 1);
                }
            }
//            }
            if (rates.size() > 0) {
                rateAnalysis.setPercent((float) ((rateAnalysis.getCounter() * 100) / rates.size()));
            }
            listOfRateAnalysis.add(rateAnalysis);
        }
        rateDTO.setRateAnalysis(listOfRateAnalysis);
        rateDTO.setRate(rates);
        return rateDTO;
    }
}



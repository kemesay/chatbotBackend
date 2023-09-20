package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Office;
import com.DXvalley.chatbot.repository.OfficeRepository;
import com.DXvalley.chatbot.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImp implements OfficeService {
    @Autowired
    OfficeRepository officeRepository;
    @Override
    public void registerOffice(Office office) {officeRepository.save(office);

    }
}

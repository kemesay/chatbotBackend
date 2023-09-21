package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Hotel;
import com.DXvalley.chatbot.repository.HotelRepository;
import com.DXvalley.chatbot.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    @Autowired
    HotelRepository hotelRepository;
    @Override
    public void registerHotel(Hotel hotel) {hotelRepository.save(hotel);
    }
    @Override
    public Hotel editHotel(Hotel hotel) {return this.hotelRepository.save(hotel);
    }
    @Override
    public List<Hotel> fetchHotels() { return hotelRepository.findAll();}
}

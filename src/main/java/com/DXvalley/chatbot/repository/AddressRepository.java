package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByAddressId (Long addressId);
}

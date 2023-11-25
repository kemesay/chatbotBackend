package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long>{
    Users findByUsername(String username);
    Optional<Users> findByEmail(String email);

    Users findByUserId (Long userId);
    Users findByEmailOrUsername(String username, String email);



}

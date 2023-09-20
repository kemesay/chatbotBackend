package com.DXvalley.chatbot.repository;
import com.DXvalley.chatbot.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long>{
    Users findByUsername(String username);
    Users findByUserId (Long userId);
    Users findByEmailOrUsername(String username, String email);
}

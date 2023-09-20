package com.DXvalley.chatbot.repository;

import com.DXvalley.chatbot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository <Role, Integer> {
    Role findByRoleName (String roleName);
}

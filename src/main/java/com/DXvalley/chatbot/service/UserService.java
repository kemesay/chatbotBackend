package com.DXvalley.chatbot.service;

import com.DXvalley.chatbot.models.Users;

import java.util.List;

public interface UserService {

    void userSignUp(Users users);

    List<Users> fetchUsers();
    Users editUser(Users users);


}

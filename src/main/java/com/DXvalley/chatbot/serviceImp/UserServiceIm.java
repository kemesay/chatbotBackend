package com.DXvalley.chatbot.serviceImp;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.UserRepository;
import com.DXvalley.chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceIm  implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public void userSignUp(Users users) {
        userRepository.save(users);
    }

    @Override
    public List<Users> fetchUsers() {
        return userRepository.findAll();
    }
    @Override
    public Users editUser(Users users) {
      return this.userRepository.save(users);
    }
}

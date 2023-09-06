package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public void writeUser(String username, String password, String confirmPassword, String email) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setEnabled(false);
        userRepository.save(user);

    }
}

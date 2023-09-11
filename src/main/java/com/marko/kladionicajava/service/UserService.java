package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void writeUser(String username, String password, String confirmPassword, String email) {
        Users user = new Users();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setEnabled(false);
        userRepository.save(user);

    }

    public Users getUser(Principal principal) {
        if (principal != null) {
            Optional<Users> optionalUsers = userRepository.findByUsername(principal.getName());
            if (optionalUsers.isEmpty()) {
                return new Users();
            }
            Users user = optionalUsers.get();
            if (user.getImage() == null) {
                user.setImage("/no-picture.jpg");
            }
            return user;
        }
        return new Users();
    }
}
package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public List<Email> getEmails() {
        try {
            return emailRepository.findAll();

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }


    }

    public void createEmail(Email email) {
        try {
            Optional<Email> optionalEmail = emailRepository.existByEmail(email.getEmail());
            if (optionalEmail.isEmpty()) {
               emailRepository.save(email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEmail(String emailId) {
        try {
            Optional<Email> optionalEmail = emailRepository.findById(emailId);
            if (optionalEmail.isPresent()) {
                emailRepository.delete(optionalEmail.get());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;
}

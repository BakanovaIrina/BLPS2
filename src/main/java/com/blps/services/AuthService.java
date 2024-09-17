package com.blps.services;

import com.blps.entity.User;
import com.blps.repository.UserXMLRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserXMLRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User parseAuthHeader(String authentication) {
        String[] credentials = authentication.split(":");
        return new User(credentials[0], credentials[1]);
    }

    public boolean authenticateUser(User user) {
        try {
            User foundByUsername = userRepository.findByUsername(user.getUsername());
            return passwordEncoder.matches(user.getPassword(), foundByUsername.getPassword());
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public boolean registerUser(User user) {
        User u = userRepository.findByUsername(user.getUsername());
        if (u == null) {
            userRepository.save(new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRole()));
            return true;
        } else {
            return false;
        }
    }
}
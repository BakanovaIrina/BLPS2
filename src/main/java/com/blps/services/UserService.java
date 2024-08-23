package com.blps.services;

import com.blps.entity.User;
import com.blps.repository.UserXMLRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserXMLRepo userRepository;

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user with username: '" + username + "' found");
        }
        return user;
    }




}

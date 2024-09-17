package com.blps.services;

import com.blps.entity.User;
import com.blps.model.UserRequest;
import com.blps.model.UserRequestWithRole;
import com.blps.repository.UserXMLRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserXMLRepo userRepository;

    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user with username: '" + username + "' found");
        }
        return user;
    }

    @Transactional(transactionManager = "transactionManager")
    public User createUserFromRequest(UserRequest userRequest) {
        return new User(userRequest.getUsername(), userRequest.getPassword());
    }

    @Transactional(transactionManager = "transactionManager")
    public User createUserWithRoleFromRequest(UserRequestWithRole userRequestWithRole) {
        return new User(userRequestWithRole.getUsername(), userRequestWithRole.getPassword(),
                userRequestWithRole.getRole());
    }
}
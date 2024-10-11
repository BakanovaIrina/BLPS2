package com.blps.controllers;

import com.blps.entity.User;
import com.blps.model.InfoResponse;
import com.blps.model.UserRequest;
import com.blps.model.UserRequestWithRole;
import com.blps.services.AuthService;
import com.blps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;


    @PostMapping("/sign-in")
    public InfoResponse signIn(@RequestBody UserRequest userRequest) {
        User user = userService.createUserFromRequest(userRequest);
        if(authService.authenticateUser(user)){
            return new InfoResponse("Пользователь " + user.getUsername() + " авторизован", 0);
        }
        return new InfoResponse("Пользователь " + user.getUsername() + " не авторизован", 1);
    }

    @PostMapping("/sign-up")
    public InfoResponse signUp(@RequestBody UserRequestWithRole userRequestWithRole){
        User user = userService.createUserWithRoleFromRequest(userRequestWithRole);
        if(authService.registerUser(user)){
            return new InfoResponse("Пользователь " + user.getUsername() + " зарегистрирован успешно", 0);
        }
        return new InfoResponse("Имя " + user.getUsername() + " занято", 1);
    }
}

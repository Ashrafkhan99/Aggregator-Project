package com.example.Aggregator.Controller;

import com.example.Aggregator.DTO.UserDto;
import com.example.Aggregator.Entity.User;
import com.example.Aggregator.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDto user) {
        User user1 = userServices.registerUser(user);
        String token = UUID.randomUUID().toString();
        String applicationUrl = "http://localhost:8080/verifyToken?token=" + token;
        System.out.println("Click the link to activate your account: " + applicationUrl);
        userServices.registerToken(user1, token);
        return user1;
    }

    @PostMapping("/verifyToken")
    public String verifyToken(@RequestParam String token) {
        boolean isValid = userServices.validateToken(token);
        if (isValid) {
            userServices.enableUser(token);
            return "Token is valid and user is activated";
        }
        return "Token is invalid or expired";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody UserDto user) {
        String jwtToken = userServices.login(user);
        if (jwtToken != null) {
            return jwtToken;
        } else {
            return "Invalid credentials";
        }
    }

    @GetMapping("/tokenHello")
    public String tokenHello() {
        return "Hello from tokenHello!";
    }
}

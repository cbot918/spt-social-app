package com.yale.fullstackbackend.controller;
import com.yale.fullstackbackend.model.User;
import com.yale.fullstackbackend.repository.UserRepository;
import com.yale.fullstackbackend.util.JwtUtil;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.Date;
import io.jsonwebtoken.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/test")
    public String printTest(){
        System.out.println("test api");
        return "test api";
    }

    @GetMapping("/user")
    List<User> getAllUser(){
        return userRepository.findAll();
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id);
        }
    }

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/jwt/generate")
    public Map<String, Object> authJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("language", "java");

        String jwtToken = jwtUtil.generateToken(claims, "Java Programmer");

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        return response;
    }
    // 先補上驗證Token API實作方法
    @GetMapping("/jwt/auth")
    public Map<String, Object> authJwt(
            @RequestHeader Map<String, String> headers
    ) {

        String clientToken = headers.get("authorization");

        Claims data = jwtUtil.parseToken(clientToken);
        String sub = data.get("sub", String.class);

        Boolean status = sub instanceof String;
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("sub", sub);
        return response;
    }
}

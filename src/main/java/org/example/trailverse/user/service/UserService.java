package org.example.trailverse.user.service;

import lombok.RequiredArgsConstructor;
import org.example.trailverse.user.LoginRequest;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    public List<User> getUser(){
        return userRepository.findAll();
    }
    public User findUserId(String userId){
        return userRepository.findByUserId(userId);
    }

    public User login(LoginRequest req){
        User user = userRepository.findByUserId(req.getLoginId());

        return  user;
    }

}

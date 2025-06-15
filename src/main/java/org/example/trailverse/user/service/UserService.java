package org.example.trailverse.user.service;

import lombok.RequiredArgsConstructor;
import org.example.trailverse.user.LoginRequest;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;


    public List<User> getUser(){
        return userRepository.findAll();
    }

    public User findUserId(String userId){
        log.info("나왔을까요? user:{}",userId);
        User user = userRepository.findByUserId(userId);
        log.info("userId is Id:{}", user.getId());
        return user;
    }

//    public User login(LoginRequest req){
//        User user = userRepository.findByUserId(req.getLoginId());
//        return  user;
//    }
//    public Long findUserIdLong(String userId){
//        log.info("나왔을까요id? user:{}",userId);
//        Long user= userRepository.findByUserid(userId);
//        log.info("유저아이디값:{}",user);
//        return user;
//    }

}

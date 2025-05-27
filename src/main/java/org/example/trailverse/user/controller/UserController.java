package org.example.trailverse.user.controller;


import Util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.trailverse.user.LoginRequest;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.Authenticator;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class UserController {
    private final UserService userService;

    @GetMapping("/userlist")
    public List<User> userList() {
        return userService.getUser();
    }

    @GetMapping("/findByuserID")//findByuserID?userId=20230857
    public User userId(@RequestParam("userId") String userId) {
        return userService.findUserId(userId);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest);
        if (user == null) {
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }
        String secret = "my-very-strong-secret-key-which-is-32bytes!";
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        long expireTimeMs = 1000 * 60 * 60;//유효시간 1시간
        String jwtToken = JwtTokenUtil.createToken(user.getUserId(), secretKey, expireTimeMs);
        return jwtToken;
    }

    @GetMapping("/info")
    public String userInfo(Authentication authentication){
        User loginUser = userService.findUserId(authentication.name());

        return String.format("loginId : %s\nnickname : %s",
                loginUser.getUserId(), loginUser.getUserNickname());
    }
}

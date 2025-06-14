package org.example.trailverse.user.controller;


import lombok.RequiredArgsConstructor;
import org.example.trailverse.review.domain.Review;
import org.example.trailverse.review.dto.ReviewDto;
import org.example.trailverse.route.domain.HikingPath;
import org.example.trailverse.route.domain.HikingSession;
import org.example.trailverse.route.dto.HikingSessionResponseDto;
import org.example.trailverse.route.service.HikingService;
import org.example.trailverse.user.dto.UserDto;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trailvers")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final HikingService hikingService;

    @GetMapping("/userlist")
    public List<User> userList() {
        return userService.getUser();
    }

    @GetMapping("/findByUserID")//findByuserID?userId=20230857
    public User userId(@RequestParam("userId") String userId) {
        return userService.findUserId(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        log.info("사용자 들어왔니?:[][]",userDto.getUserId(),userDto.getPassword());
        User user = userService.findUserId(userDto.getUserId());
        if(user==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 사용자명을 가진 유저 존재 않습니다. 다시 로그인해주세요");

        }
        if(!user.getUserPw().equals(userDto.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("비밀번호가 올바르지 않습니다. 다시 로그인하세요");
        }
        return ResponseEntity.ok(UserDto.from(user));
    }


//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        User user = userService.login(loginRequest);
//        if (user == null) {
//            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
//        }
//        String secret = "my-very-strong-secret-key-which-is-32bytes!";
//        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//        long expireTimeMs = 1000 * 60 * 60;//유효시간 1시간
//        String jwtToken = JwtTokenUtil.createToken(user.getUserId(), secretKey, expireTimeMs);
//        return jwtToken;
    }

//    @GetMapping("/info")
//    public String userInfo(Authentication authentication){
//        User loginUser = userService.findUserId(authentication.name());
//
//        return String.format("loginId : %s\nnickname : %s",
//                loginUser.getUserId(), loginUser.getUserNickname());
//    }

//    @GetMapping(value = "/")
//    public List<User> showInfo(@RequestParam String userId){
//        User id = userService.findUserId(userId);
//        //등산 횟수, 누적거리를 routeService 에서 id로 찾아서 가져외기
//        //아직 등록하지 않은 리뷰들 리스트 가져오기
//        return
//    }


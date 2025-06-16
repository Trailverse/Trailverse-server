package org.example.trailverse.route.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trailverse.review.service.ReviewService;
import org.example.trailverse.route.domain.HikingSession;
import org.example.trailverse.route.dto.HikingSessionRequestDto;
import org.example.trailverse.route.dto.HikingSessionResponseDto;
import org.example.trailverse.route.service.HikingService;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hiking")
@Validated
public class HikingController {

    private final HikingService hikingService;
    private final UserService userService;
    private final ReviewService reviewService;

    public HikingController(HikingService hikingService, UserService userService, ReviewService reviewService) {
        this.hikingService = hikingService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    Logger log = LoggerFactory.getLogger(HikingController.class);


    /**
     * 안드로이드 앱에서 전송한 등산 데이터를 저장합니다
     */
    @PostMapping("/sessions")
    public ResponseEntity<Response> saveHikingSession(@RequestBody HikingSessionRequestDto requestDto) {

        log.info("등산 세션 저장 요청 수신 - 사용자: {}, 시작시간: {}",
                requestDto.getUserId(), requestDto.getStartTime());
        User user = userService.findUserId(requestDto.getUserId());
        try {
            HikingSessionResponseDto response = hikingService.saveHikingSession(requestDto,user);

            log.info("등산 세션 저장 성공 - 세션 ID: {}", response.getSessionId());

//            User user = userService.findUserId(requestDto.getUserId());
            if (user == null) {
                log.error("User not found for ID: {}", user.getUserId());
            }
            HikingSession savedSession = hikingService.getHikingSessionById(response.getSessionId());
            
            reviewService.resetSave(user,savedSession);
            

            return ResponseEntity.status(HttpStatus.CREATED).body(new Response(200, "등산 저장 성공 및 리뷰 초기화 성공", response));

        } catch (Exception e) {
            log.error("등산 세션 저장 실패: {}", e.getMessage(), e);

//            HikingSessionResponseDto errorResponse = new HikingSessionResponseDto
//                    .message("등산 기록 저장에 실패했습니다: " + e.getMessage())
//                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(200, "등산 기록 저장에 실패했습니다.", null));
            // {status: 200, message: "" , data: {?????}
        }
    }

    public static class Response {
        int status;
        String message;
        Object data;

        public Response(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public int getStatus() { return status; }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }

    /**
     * 특정 사용자의 등산 기록 목록을 조회합니다
     */
    @GetMapping("/sessions/user/{userId}")
    public ResponseEntity<List<HikingSession>> getHikingSessionsByUserId(
            @PathVariable String userId) {
        User user = userService.findUserId(userId);
        log.info("사용자 {}의 등산 기록 조회 요청", userId);

        try {
            List<HikingSession> sessions = hikingService.getHikingSessionsByUserId(user);
            return ResponseEntity.ok(sessions);

        } catch (Exception e) {
            log.error("등산 기록 조회 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 특정 세션 ID의 등산 기록을 상세 조회합니다
     */
    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<HikingSession> getHikingSessionById(
            @PathVariable Long sessionId) {

        log.info("세션 ID {}의 등산 기록 조회 요청", sessionId);

        try {
            HikingSession session = hikingService.getHikingSessionById(sessionId);
            return ResponseEntity.ok(session);

        } catch (Exception e) {
            log.error("등산 기록 조회 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 서버 상태 확인용 엔드포인트
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Hiking API 서버가 정상 작동 중입니다.");
    }
}

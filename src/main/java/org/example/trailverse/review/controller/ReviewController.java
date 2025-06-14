package org.example.trailverse.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.trailverse.review.domain.Review;
import org.example.trailverse.review.dto.*;
import org.example.trailverse.review.service.ReviewService;
import org.example.trailverse.route.domain.HikingSession;

import org.example.trailverse.route.dto.HikingSessionResponseDto;
import org.example.trailverse.route.service.HikingService;

import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final HikingService hikingService;
    private final UserService userService;

    //별점을 눌렀을때 그 길에 대한 리뷰전테 조회->별점을 루르면 화면 전화할때 주소
    @GetMapping("/byAll")
    public List<CompletedReviewDto> byReviewAll(@RequestParam Long routeId){
        HikingSession routeID = hikingService.getHikingSessionById(routeId);
//        Route routeID= routeServices.findById(path_id);
        log.info("길id:{}",routeID.getSessionId());
        return reviewService.findByRouteReviewAll(routeID);
    }

    //리뷰 상세페이지에서 등록 버튼
    @PutMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//이걸 작성할때는 updat로 해야함
    public ResponseEntity<?> reviewWrite(@RequestPart(name = "key", required = true) ReviewDto reviewDto, @RequestPart(value = "file", required = false) MultipartFile multipartFile){
        log.info("잘왔을까요?:[][][]][]",reviewDto.getReviewId(),reviewDto.getRouteId(),reviewDto.getUserId(),reviewDto.getReviewText());
        reviewService.writeReview(reviewDto,multipartFile);
        return ResponseEntity.ok(Map.of("message","리뷰등록 성공"));
    }

    //저장돼었을대 userid가 routeid사용한 받아오면 초기롸롤 null로 하고 초기 save로 해야함
    @PostMapping(value = "/reset")
    public ResponseEntity<?> reset(@RequestBody ResetReviewDto request){
        User user = userService.findUserId(request.getUserId());
        HikingSession route = hikingService.getHikingSessionById(request.getRouteId());
        reviewService.resetSave(user,route);
        return ResponseEntity.ok(Map.of("message", "리뷰초기화 완료"));
    }

    @GetMapping(value = "/myPage")//작성해야하는 리뷰 리스트 반환
    public List<ReviewDto> viewList(@RequestParam String userId){
        log.info("들어왓나?:{}",userId);

       User user = userService.findUserId(userId);

        log.info("id값이 나올까요?:{}",user.getUserId());

        List<Review> reviews =  reviewService.findEmptyReview(user);

        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for(Review review : reviews){
            reviewDtoList.add(ReviewDto.from(review));
        }
        return reviewDtoList;
    }
    //리뷰쓰기 번트 -> 리뷰 상세페이지 get
    @GetMapping("/detailReview")
    public CompletedReviewDto detailReviewPage(@RequestParam Long reviewId){
        return reviewService.detail(reviewId);
    }

//    @GetMapping("/myPage")
//    public List<ReviewDto> myPage(@RequestParam String userId){
//        List<HikingSessionResponseDto> reviewDtoList = new ArrayList<>();
//        User user = userService.findUserId(userId);
//        if(user == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다: " + userId);
//        }
//        List<HikingSession> reviews = hikingService.getHikingSessionsByUserId(userId);
//
//        for(HikingSession review : reviews){
//            reviewDtoList.add(HikingSessionResponseDto);
//        }
//        return reviewDtoList;
//    }
}


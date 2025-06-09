package org.example.trailverse.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.trailverse.review.domain.Review;
import org.example.trailverse.review.dto.ResetReviewDto;
import org.example.trailverse.review.dto.ReviewDto;
import org.example.trailverse.review.dto.RouteIdDto;
import org.example.trailverse.review.dto.UserIdDto;
import org.example.trailverse.review.service.ReviewService;
import org.example.trailverse.route.domain.Route;
import org.example.trailverse.route.service.RouteGpsService;
import org.example.trailverse.route.service.RouteService;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final RouteService routeServices;
    private final UserService userService;

    //별점을 눌렀을때 그 길에 대한 리뷰전테 조회->별점을 루르면 화면 전화할때 주소
    @GetMapping("/byAll")
    public List<ReviewDto> byReviewAll(@RequestBody RouteIdDto routeIdDto){
        Long reId = routeIdDto.getRouteId();
        Route routeID= routeServices.findById(reId);
        log.info("길id:{}",routeID.getId());
        return reviewService.findByRouteReviewAll(routeID);
    }
    //길따라가기 이용시 마이페이제에서 리뷰하기->길 잣성
    @PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//이걸 작성할때는 updat로 해야함
    public ResponseEntity<?> reviewWrite(@RequestPart("key") ReviewDto reviewDto, @RequestPart(value = "file", required = false) MultipartFile multipartFile){
       Route route = routeServices.findById(reviewDto.getRouteId());
        User user = userService.findUserId(reviewDto.getUserId());
        reviewService.writeReview(reviewDto,user,route,multipartFile);
        return ResponseEntity.ok(Map.of("message","리뷰등록 성공"));
    }


    //저장돼었을대 userid가 routeid사용한 받아오면 초기롸롤 null로 하고 초기 save로 해야함
    @PostMapping(value = "/reset")
    public ResponseEntity<?> reset(@RequestBody ResetReviewDto request){
        User user = userService.findUserId(request.getUserId());
        Route route = routeServices.findById(request.getRouteId());
        reviewService.resetSave(user,route);
        return ResponseEntity.ok(Map.of("message", "리뷰초기화 완료"));
    }

    @GetMapping(value = "/viewReview")
    public List<ReviewDto> viewList(@RequestBody UserIdDto userId){
        log.info("들어왓나?:{}",userId);
        String userid = userId.getUserId();

       User user = userService.findUserId(userid);

        log.info("id값이 나올까요?:{}",user.getUserId());

        List<Review> reviews =  reviewService.findEmptyReview(user);

        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for(Review review : reviews){
            reviewDtoList.add(ReviewDto.from(review));
        }
        return reviewDtoList;
    }


}

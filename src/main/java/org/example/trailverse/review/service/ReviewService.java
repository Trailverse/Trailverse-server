package org.example.trailverse.review.service;

import lombok.RequiredArgsConstructor;
import org.example.trailverse.review.domain.Review;
import org.example.trailverse.review.dto.ReviewDto;
import org.example.trailverse.review.repository.ReviewRepository;
import org.example.trailverse.route.domain.Route;
import org.example.trailverse.user.domain.User;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //별점눌렀을때 각 길에 대한 리뷰 전체 조회
    public List<ReviewDto> findByRouteReviewAll(Route route){
        List<Review> route1= reviewRepository.findByRoute(route);

        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for(Review review : route1){
            reviewDtoList.add(ReviewDto.from(review));
        }
        return reviewDtoList;
    }





    //마이페이지에서 리뷰작성
    public void writeReview(ReviewDto reviewDto, User user, Route route, MultipartFile multipartFile){
        String imagePath = saveImage(multipartFile);
        Review reviews = Review.from(reviewDto,route,user,imagePath);
        reviewRepository.save(reviews);
    }
    //이미지
    private String saveImage(MultipartFile multipartFile){
        String fileName = UUID.randomUUID()+"_"+multipartFile.getOriginalFilename();
        Path path = Paths.get("uploads/"+fileName);
        try {
            Files.copy(multipartFile.getInputStream(), path);
        }catch (IOException e){
            throw new RuntimeException("이미지 저장 실패",e);
        }
        return path.toString();
    }
    //경로종료시 발생하는 리뷰 초기화
    public void resetSave(User user, Route route){

        Review review = new Review();
        review.setReviewText(null);
        review.setImg(null);
        review.setUser(user);
        review.setRoute(route);
        reviewRepository.save(review);

    }


    public List<Review> findEmptyReview(User user){
        return reviewRepository.findByUser(user);
    }
}

package org.example.trailverse.review.service;

import lombok.RequiredArgsConstructor;
import org.example.trailverse.review.domain.Review;
import org.example.trailverse.review.dto.CompletedReviewDto;
import org.example.trailverse.review.dto.ReviewDto;
import org.example.trailverse.review.repository.ReviewRepository;
import org.example.trailverse.route.domain.HikingSession;

import org.example.trailverse.user.domain.User;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //별점눌렀을때 각 길에 대한 리뷰 전체 조회
    public List<CompletedReviewDto> findByRouteReviewAll(HikingSession route){
        List<Review> route1= reviewRepository.findByRoute(route);

        List<CompletedReviewDto> reviewDtoList = new ArrayList<>();
        for(Review review : route1){
            reviewDtoList.add(CompletedReviewDto.from(review));
        }
        return reviewDtoList;
    }

    //마이페이지에서 리뷰작성
    public void writeReview(ReviewDto reviewDto,MultipartFile multipartFile){
        String imagePath = saveImage(multipartFile);
        Review review = findReviewId(reviewDto.getReviewId());
        if(review!=null){
            review.setReviewText(reviewDto.getReviewText());
            review.setImg(imagePath);
        }
        reviewRepository.save(review);
    }
    //이미지
    private String saveImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        Path uploadDir = Paths.get("uploads");
        Path path = uploadDir.resolve(fileName);

        try {
            // uploads 폴더가 없으면 생성
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }

        return path.toString();
    }

    //    private String saveImage(MultipartFile multipartFile){
//        String fileName = UUID.randomUUID()+"_"+multipartFile.getOriginalFilename();
//        Path path = Paths.get("uploads");
//        try {
//            Files.copy(multipartFile.getInputStream(), path);
//        }catch (IOException e){
//            throw new RuntimeException("이미지 저장 실패",e);
//        }
//        return path.toString();
//    }
    //경로종료시 발생하는 리뷰 초기화
    public void resetSave(User user, HikingSession route){

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
    public Review findReviewId(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(()->new NullPointerException("해당 리뷰는 못찼겠는데요 님이 잘못한듯"));

    }
    public CompletedReviewDto detail(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new NullPointerException("해당리뷰아디로 상세페이지 하려니까 아이디가 없네요 다시해오세요"));
        return CompletedReviewDto.from(review);
    }
}

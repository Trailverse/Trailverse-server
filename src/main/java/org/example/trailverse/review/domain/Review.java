package org.example.trailverse.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.trailverse.review.dto.ReviewDto;
import org.example.trailverse.route.domain.Route;
import org.example.trailverse.user.domain.User;
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewText;

    private String img;

    @ManyToOne
    private Route route; // 어떤 산
    @ManyToOne
    private User user; //누가


    public static Review from(ReviewDto reviewDto,Route route,User user,String image){
        return Review.builder()
                .reviewText(reviewDto.getReviewText())
                .route(route)
                .user(user)
                .img(image)
                .build();
    }

    public void setReviewText(String reviewText){
        this.reviewText=reviewText;
    }
    public void setImg(String img){
        this.img = img;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setRoute(Route route){
        this.route = route;
    }
}

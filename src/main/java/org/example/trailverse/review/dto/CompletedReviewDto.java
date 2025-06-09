package org.example.trailverse.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.trailverse.review.domain.Review;

@Builder
@Getter
public class CompletedReviewDto {
    private Long reviewId;
    private Long routeId;
    private String userId;
    private String reviewText;
    private String img;
    public static CompletedReviewDto from(Review review){
        return CompletedReviewDto.builder()
                .reviewId(review.getId())
                .routeId(review.getRoute().getId())
                .userId(review.getUser().getUserId())
                .reviewText(review.getReviewText())
                .img(review.getImg())
                .build();
    }
}

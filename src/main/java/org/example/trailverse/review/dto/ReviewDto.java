package org.example.trailverse.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.trailverse.review.domain.Review;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long routeId;
    private String userId;
    private String reviewText;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
                .reviewId(review.getId())
                .routeId(review.getRoute().getSessionId())
                .userId(review.getUser().getUserId())
                .reviewText(review.getReviewText())
                .build();
    }

}

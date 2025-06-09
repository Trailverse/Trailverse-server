package org.example.trailverse.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.trailverse.review.domain.Review;

import java.util.List;

@Getter
@Builder
public class ReviewDto {
//    private Long id;
    private Long routeId;
    private String userId;
//    private String img;//fs
    private String reviewText;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
//                .id(review.getId())
                .routeId(review.getRoute().getId())
                .userId(review.getUser().getUserId())
//                .img(review.getImg())
                .reviewText(review.getReviewText())
                .build();
    }
//    public static List<ReviewDto> fromList(List<Review>)
}

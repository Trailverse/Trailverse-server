package org.example.trailverse.review.repository;

import org.example.trailverse.review.domain.Review;
import org.example.trailverse.route.domain.Route;
import org.example.trailverse.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRoute(Route route);
    List<Review> findByUser(User user);
}

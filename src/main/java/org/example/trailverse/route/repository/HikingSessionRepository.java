package org.example.trailverse.route.repository;

import org.example.trailverse.route.domain.HikingSession;
import org.example.trailverse.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HikingSessionRepository extends JpaRepository<HikingSession, Long> {

    // 특정 사용자의 등산 기록 조회
    List<HikingSession> findByUserOrderByStartTimeDesc(User userId);

    // 특정 기간의 등산 기록 조회
    @Query("SELECT h FROM HikingSession h WHERE h.user = :userId AND h.startTime BETWEEN :startDate AND :endDate")
    List<HikingSession> findByUserIdAndDateRange(@Param("userId") User userId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}

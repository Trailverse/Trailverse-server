package org.example.trailverse.route.repository;

import org.example.trailverse.route.domain.HikingPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HikingPathRepository extends JpaRepository<HikingPath, Long> {

    // 특정 세션의 경로 포인트들을 순서대로 조회
    List<HikingPath> findByHikingSession_SessionIdOrderBySequenceOrder(Long sessionId);
}

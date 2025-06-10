package org.example.trailverse.route.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trailverse.route.domain.HikingPath;
import org.example.trailverse.route.domain.HikingSession;
import org.example.trailverse.route.dto.HikingSessionRequestDto;
import org.example.trailverse.route.dto.HikingSessionResponseDto;
import org.example.trailverse.route.dto.PathPointDto;
import org.example.trailverse.route.repository.HikingSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HikingService {

    private final HikingSessionRepository hikingSessionRepository;

    Logger log = LoggerFactory.getLogger(HikingService.class);

    public HikingService(HikingSessionRepository hikingSessionRepository) {
        this.hikingSessionRepository = hikingSessionRepository;
    }

    /**
     * 등산 세션 데이터를 저장합니다
     */
    public HikingSessionResponseDto saveHikingSession(HikingSessionRequestDto requestDto) {
        try {
            log.info("등산 세션 저장 시작 - 사용자: {}", requestDto.getUserId());

            // HikingSession 엔티티 생성
            HikingSession hikingSession = HikingSession.toEntity(
                    requestDto.getUserId(),
                    requestDto.getStartTime(),
                    requestDto.getEndTime(),
                    requestDto.getTotalDistance());

            // 경로 포인트들을 HikingPath 엔티티로 변환하여 추가
            List<PathPointDto> pathPoints = requestDto.getPath();
            for (int i = 0; i < pathPoints.size(); i++) {
                PathPointDto pointDto = pathPoints.get(i);

                HikingPath hikingPath = HikingPath.toEntity(
                        pointDto.getLatitude(),
                        pointDto.getLongitude());

                hikingPath.upSequenceOrder(hikingPath.getSequenceOrder());

                hikingSession.addPath(hikingPath);
            }

            // 데이터베이스에 저장
            HikingSession savedSession = hikingSessionRepository.save(hikingSession);

            log.info("등산 세션 저장 완료 - 세션 ID: {}, 경로 포인트 수: {}",
                    savedSession.getSessionId(), pathPoints.size());

            // 응답 DTO 생성
            return new HikingSessionResponseDto(
                    savedSession.getSessionId(),
                    savedSession.getUserId(),
                    savedSession.getStartTime(),
                    savedSession.getEndTime(),
                    savedSession.getTotalDistance(),
                    savedSession.getCreatedAt(),
                    "등산 기록이 성공적으로 저장되었습니다.");

        } catch (Exception e) {
            log.error("등산 세션 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("등산 기록 저장에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 특정 사용자의 등산 기록 목록을 조회합니다
     */
    @Transactional(readOnly = true)
    public List<HikingSession> getHikingSessionsByUserId(String userId) {
        log.info("사용자 {}의 등산 기록 조회", userId);
        return hikingSessionRepository.findByUserIdOrderByStartTimeDesc(userId);
    }

    /**
     * 특정 세션 ID로 등산 기록을 조회합니다
     */
    @Transactional(readOnly = true)
    public HikingSession getHikingSessionById(Long sessionId) {
        log.info("세션 ID {}의 등산 기록 조회", sessionId);
        return hikingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("해당 등산 기록을 찾을 수 없습니다."));
    }
}


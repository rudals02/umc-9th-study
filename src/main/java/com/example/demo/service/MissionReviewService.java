package com.example.demo.service;

import com.example.demo.repository.MissionReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service 계층에서의 사용
 */
@Service
@RequiredArgsConstructor
public class MissionReviewService {

    private final com.example.demo.service.MissionReviewRepository missionReviewRepository;

    /**
     * 리뷰 작성 메서드
     * @param userId 작성자 ID
     * @param missionId 미션 ID
     * @param rating 별점 (1~5)
     * @param content 리뷰 내용
     */
    @Transactional  // 데이터 변경 작업이므로 트랜잭션 필요
    public void createReview(Long userId, Long missionId, Integer rating, String content) {
        // 1. User, Mission 엔티티 조회 (실제로는 UserRepository, MissionRepository 필요)
        SecurityProperties.User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new EntityNotFoundException("미션을 찾을 수 없습니다."));

        // 2. MissionReview 엔티티 생성
        MissionReview review = MissionReview.builder()
                .user(user)                      // 작성자 설정
                .mission(mission)                // 미션 설정
                .rating(rating)                  // 별점
                .content(content)                // 리뷰 내용
                .createdAt(LocalDateTime.now())  // 작성 시간
                .build();

        // 3. 저장 (JPA가 자동으로 INSERT 쿼리 생성)
        missionReviewRepository.save(review);
    }
}
package com.example.demo.repository;

import com.example.demo.dto.UserSummaryDto;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SecurityProperties.User, Long> {

    /**
     * 사용자 요약 정보 조회 - @Query 방식
     *
     * @param userId 조회할 사용자 ID
     * @return 사용자 요약 정보 DTO
     */
    @Query("""
        SELECT new com.example.dto.UserSummaryDto(
            u.id,
            u.name,
            u.email,
            COALESCE(SUM(ms.earnedPoints), 0L),
            COUNT(DISTINCT mr.reviewId)
        )
        FROM User u
        LEFT JOIN MissionStatus ms ON u.id = ms.user.id
        LEFT JOIN MissionReview mr ON u.id = mr.user.id
        WHERE u.id = :userId
        GROUP BY u.id, u.name, u.email
        """)
    UserSummaryDto findUserSummary(@Param("userId") Long userId);

    /**
     * Optional 반환 버전 (사용자가 없을 경우 대비)
     */
    @Query("""
        SELECT new com.example.dto.UserSummaryDto(
            u.id,
            u.name,
            u.email,
            COALESCE(SUM(ms.earnedPoints), 0L),
            COUNT(DISTINCT mr.reviewId)
        )
        FROM User u
        LEFT JOIN MissionStatus ms ON u.id = ms.user.id
        LEFT JOIN MissionReview mr ON u.id = mr.user.id
        WHERE u.id = :userId
        GROUP BY u.id, u.name, u.email
        """)
    Optional<UserSummaryDto> findUserSummaryOptional(@Param("userId") Long userId);
}
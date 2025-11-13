package com.example.umc.repository;

import com.example.umc.domain.Mission;
import com.example.umc.domain.User;
import com.example.umc.domain.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    // 사용자의 특정 미션 조회
    Optional<UserMission> findByUserAndMission(User user, Mission mission);

    // 사용자의 진행중인 미션 목록
    @Query("SELECT um FROM UserMission um WHERE um.user = :user AND um.status = 'IN_PROGRESS' ORDER BY um.createdAt DESC")
    List<UserMission> findInProgressMissionsByUser(@Param("user") User user);

    // 사용자의 완료된 미션 목록
    @Query("SELECT um FROM UserMission um WHERE um.user = :user AND um.status = 'COMPLETED' ORDER BY um.completedAt DESC")
    List<UserMission> findCompletedMissionsByUser(@Param("user") User user);

    // 사용자의 모든 미션 조회
    @Query("SELECT um FROM UserMission um WHERE um.user = :user ORDER BY um.createdAt DESC")
    List<UserMission> findAllByUser(@Param("user") User user);

    // 특정 상태의 미션 개수
    @Query("SELECT COUNT(um) FROM UserMission um WHERE um.user = :user AND um.status = :status")
    Long countByUserAndStatus(@Param("user") User user, @Param("status") UserMission.MissionStatus status);

    // 사용자가 미션 참여 중인지 확인
    boolean existsByUserAndMission(User user, Mission mission);
}
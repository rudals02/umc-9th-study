package com.example.umc.repository;

import com.example.umc.domain.User;
import com.example.umc.domain.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query("SELECT um FROM UserMission um " +
            "JOIN FETCH um.mission m " +
            "WHERE um.user = :user AND um.status = 'IN_PROGRESS' " +
            "ORDER BY um.createdAt DESC")
    Page<UserMission> findInProgressMissionsByUser(@Param("user") User user, Pageable pageable);
}
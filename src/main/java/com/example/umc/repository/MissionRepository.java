package com.example.umc.repository;

import com.example.umc.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 활성화된 미션만 조회
    @Query("SELECT m FROM Mission m WHERE m.isActive = true ORDER BY m.createdAt DESC")
    List<Mission> findAllActive();

    // 미션 타입별 조회
    @Query("SELECT m FROM Mission m WHERE m.type = :type AND m.isActive = true ORDER BY m.createdAt DESC")
    List<Mission> findByTypeAndActive(@Param("type") Mission.MissionType type);

    // 특정 미션 조회 (활성화된 것만)
    @Query("SELECT m FROM Mission m WHERE m.id = :id AND m.isActive = true")
    Optional<Mission> findByIdAndActive(@Param("id") Long id);
}
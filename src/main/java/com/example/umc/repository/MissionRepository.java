package com.example.umc.repository;

import com.example.umc.domain.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m WHERE m.isActive = true ORDER BY m.createdAt DESC")
    Page<Mission> findActiveStoreMissions(Pageable pageable);
}
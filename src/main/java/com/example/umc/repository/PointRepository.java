package com.example.umc.repository;

import com.example.umc.domain.Point;
import com.example.umc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    // 사용자의 포인트 내역 조회 (최신순, 페이징)
    @Query("SELECT p FROM Point p WHERE p.user = :user ORDER BY p.createdAt DESC")
    Page<Point> findByUserOrderByCreatedAtDesc(@Param("user") User user, Pageable pageable);

    // 사용자의 총 적립 포인트
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Point p WHERE p.user = :user AND p.amount > 0")
    Integer getTotalEarnedPoints(@Param("user") User user);

    // 사용자의 총 사용 포인트
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Point p WHERE p.user = :user AND p.amount < 0")
    Integer getTotalUsedPoints(@Param("user") User user);

    // 타입별 포인트 내역
    @Query("SELECT p FROM Point p WHERE p.user = :user AND p.type = :type ORDER BY p.createdAt DESC")
    List<Point> findByUserAndType(@Param("user") User user, @Param("type") Point.PointType type);
}
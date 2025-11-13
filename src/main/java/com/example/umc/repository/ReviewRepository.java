package com.example.umc.repository;

import com.example.umc.domain.Review;
import com.example.umc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 삭제되지 않은 리뷰 조회
    @Query("SELECT r FROM Review r WHERE r.isDeleted = false AND r.id = :id")
    Optional<Review> findByIdAndNotDeleted(@Param("id") Long id);

    // 사용자별 리뷰 목록 (최신순, 페이징)
    @Query("SELECT r FROM Review r WHERE r.user = :user AND r.isDeleted = false ORDER BY r.createdAt DESC")
    Page<Review> findByUserAndNotDeleted(@Param("user") User user, Pageable pageable);

    // 전체 리뷰 목록 (최신순, 페이징)
    @Query("SELECT r FROM Review r WHERE r.isDeleted = false ORDER BY r.createdAt DESC")
    Page<Review> findAllNotDeleted(Pageable pageable);

    // 별점별 리뷰 조회
    @Query("SELECT r FROM Review r WHERE r.rating = :rating AND r.isDeleted = false ORDER BY r.createdAt DESC")
    Page<Review> findByRatingAndNotDeleted(@Param("rating") Integer rating, Pageable pageable);

    // 사용자의 총 리뷰 수
    @Query("SELECT COUNT(r) FROM Review r WHERE r.user = :user AND r.isDeleted = false")
    Long countByUserAndNotDeleted(@Param("user") User user);
}
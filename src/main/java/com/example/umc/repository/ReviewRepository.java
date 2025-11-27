package com.example.umc.repository;

import com.example.umc.domain.Review;
import com.example.umc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.user = :user AND r.isDeleted = false ORDER BY r.createdAt DESC")
    Page<Review> findByUserAndNotDeleted(@Param("user") User user, Pageable pageable);
}
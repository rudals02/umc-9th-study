package com.example.umc.repository;

import com.example.umc.domain.Review;
import com.example.umc.domain.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    // 특정 리뷰의 댓글 목록 (삭제되지 않은 것만)
    @Query("SELECT rc FROM ReviewComment rc WHERE rc.review = :review AND rc.isDeleted = false ORDER BY rc.createdAt ASC")
    List<ReviewComment> findByReviewAndNotDeleted(@Param("review") Review review);

    // 삭제되지 않은 댓글 조회
    @Query("SELECT rc FROM ReviewComment rc WHERE rc.id = :id AND rc.isDeleted = false")
    Optional<ReviewComment> findByIdAndNotDeleted(@Param("id") Long id);

    // 특정 리뷰의 댓글 수
    @Query("SELECT COUNT(rc) FROM ReviewComment rc WHERE rc.review = :review AND rc.isDeleted = false")
    Long countByReviewAndNotDeleted(@Param("review") Review review);
}
package com.example.umc.service;

import com.example.umc.domain.Point;
import com.example.umc.domain.Review;
import com.example.umc.domain.User;
import com.example.umc.dto.review.ReviewDto;
import com.example.umc.exception.BusinessException;
import com.example.umc.exception.ErrorCode;
import com.example.umc.repository.PointRepository;
import com.example.umc.repository.ReviewRepository;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    private static final int REVIEW_REWARD_POINTS = 500; // 리뷰 작성 보상 포인트

    /**
     * 리뷰 작성
     */
    @Transactional
    public ReviewDto.Response createReview(Long userId, ReviewDto.CreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Review review = Review.builder()
                .user(user)
                .rating(request.getRating())
                .content(request.getContent())
                .imageUrls(request.getImageUrls())
                .build();

        reviewRepository.save(review);

        // 리뷰 작성 보상 포인트 적립
        user.addPoints(REVIEW_REWARD_POINTS);
        Point point = Point.builder()
                .user(user)
                .amount(REVIEW_REWARD_POINTS)
                .type(Point.PointType.REVIEW_REWARD)
                .description("리뷰 작성 보상")
                .build();
        pointRepository.save(point);

        log.info("리뷰 작성 완료: userId={}, reviewId={}, points={}", userId, review.getId(), REVIEW_REWARD_POINTS);

        return ReviewDto.Response.from(review);
    }

    /**
     * 리뷰 상세 조회
     */
    public ReviewDto.DetailResponse getReview(Long reviewId) {
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewDto.DetailResponse.from(review);
    }

    /**
     * 리뷰 목록 조회 (페이징)
     */
    public ReviewDto.ListResponse getReviews(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllNotDeleted(pageable);

        return ReviewDto.ListResponse.builder()
                .reviews(reviewPage.getContent().stream()
                        .map(ReviewDto.Response::from)
                        .toList())
                .totalPages(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .currentPage(reviewPage.getNumber())
                .pageSize(reviewPage.getSize())
                .build();
    }

    /**
     * 내 리뷰 목록 조회
     */
    public ReviewDto.ListResponse getMyReviews(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Page<Review> reviewPage = reviewRepository.findByUserAndNotDeleted(user, pageable);

        return ReviewDto.ListResponse.builder()
                .reviews(reviewPage.getContent().stream()
                        .map(ReviewDto.Response::from)
                        .toList())
                .totalPages(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .currentPage(reviewPage.getNumber())
                .pageSize(reviewPage.getSize())
                .build();
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public ReviewDto.Response updateReview(Long userId, Long reviewId, ReviewDto.UpdateRequest request) {
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        // 작성자 본인 확인
        if (!review.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_REVIEW_ACCESS);
        }

        review.update(request.getRating(), request.getContent(), request.getImageUrls());

        log.info("리뷰 수정 완료: userId={}, reviewId={}", userId, reviewId);

        return ReviewDto.Response.from(review);
    }

    /**
     * 리뷰 삭제 (소프트 삭제)
     */
    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        // 작성자 본인 확인
        if (!review.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_REVIEW_ACCESS);
        }

        review.softDelete();

        log.info("리뷰 삭제 완료: userId={}, reviewId={}", userId, reviewId);
    }
}
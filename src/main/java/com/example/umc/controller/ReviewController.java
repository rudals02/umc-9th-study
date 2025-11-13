package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.SuccessCode;
import com.example.umc.dto.review.ReviewDto;
import com.example.umc.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     * POST /api/reviews
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDto.Response>> createReview(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody ReviewDto.CreateRequest request) {

        ReviewDto.Response response = reviewService.createReview(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessCode.REVIEW_CREATE_SUCCESS, response));
    }

    /**
     * 리뷰 상세 조회
     * GET /api/reviews/{reviewId}
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewDto.DetailResponse>> getReview(
            @PathVariable Long reviewId) {

        ReviewDto.DetailResponse response = reviewService.getReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.REVIEW_FETCH_SUCCESS, response));
    }

    /**
     * 리뷰 목록 조회
     * GET /api/reviews?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<ApiResponse<ReviewDto.ListResponse>> getReviews(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ReviewDto.ListResponse response = reviewService.getReviews(pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.REVIEW_FETCH_SUCCESS, response));
    }

    /**
     * 내 리뷰 목록 조회
     * GET /api/reviews/my?page=0&size=10
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ReviewDto.ListResponse>> getMyReviews(
            @RequestAttribute("userId") Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ReviewDto.ListResponse response = reviewService.getMyReviews(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.REVIEW_FETCH_SUCCESS, response));
    }

    /**
     * 리뷰 수정
     * PUT /api/reviews/{reviewId}
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewDto.Response>> updateReview(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewDto.UpdateRequest request) {

        ReviewDto.Response response = reviewService.updateReview(userId, reviewId, request);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.REVIEW_UPDATE_SUCCESS, response));
    }

    /**
     * 리뷰 삭제
     * DELETE /api/reviews/{reviewId}
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long reviewId) {

        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.REVIEW_DELETE_SUCCESS));
    }
}
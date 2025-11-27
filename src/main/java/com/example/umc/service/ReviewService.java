package com.example.umc.service;

import com.example.umc.domain.Review;
import com.example.umc.domain.User;
import com.example.umc.dto.review.ReviewDto;
import com.example.umc.repository.ReviewRepository;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewDto.ReviewPageResponse getMyReviews(Long userId, Integer page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(page, 10);
        Page<Review> reviewPage = reviewRepository.findByUserAndNotDeleted(user, pageable);

        List<ReviewDto.ReviewResponse> reviews = reviewPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ReviewDto.ReviewPageResponse.builder()
                .reviews(reviews)
                .currentPage(page)
                .totalPages(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .hasNext(reviewPage.hasNext())
                .build();
    }

    private ReviewDto.ReviewResponse convertToResponse(Review review) {
        return ReviewDto.ReviewResponse.builder()
                .id(review.getId())
                .userNickname(review.getUser().getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrls(review.getImageUrls())
                .createdAt(review.getCreatedAt())
                .commentCount(review.getComments().size())
                .build();
    }
}
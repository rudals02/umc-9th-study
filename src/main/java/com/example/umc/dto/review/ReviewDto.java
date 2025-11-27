package com.example.umc.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewResponse {
        private Long id;
        private String userNickname;
        private Integer rating;
        private String content;
        private List<String> imageUrls;
        private LocalDateTime createdAt;
        private Integer commentCount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPageResponse {
        private List<ReviewResponse> reviews;
        private Integer currentPage;
        private Integer totalPages;
        private Long totalElements;
        private Boolean hasNext;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewCreateRequest {
        private Integer rating;
        private String content;
        private List<String> imageUrls;
    }
}
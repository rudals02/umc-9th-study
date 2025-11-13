package com.example.umc.dto.review;

import com.example.umc.domain.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDto {

    // 리뷰 작성 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {

        @NotNull(message = "별점은 필수입니다.")
        @Min(value = 1, message = "별점은 최소 1점입니다.")
        @Max(value = 5, message = "별점은 최대 5점입니다.")
        private Integer rating;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Size(max = 1000, message = "리뷰 내용은 최대 1000자입니다.")
        private String content;

        private List<String> imageUrls;
    }

    // 리뷰 수정 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {

        @NotNull(message = "별점은 필수입니다.")
        @Min(value = 1, message = "별점은 최소 1점입니다.")
        @Max(value = 5, message = "별점은 최대 5점입니다.")
        private Integer rating;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Size(max = 1000, message = "리뷰 내용은 최대 1000자입니다.")
        private String content;

        private List<String> imageUrls;
    }

    // 리뷰 응답
    @Getter
    @Builder
    public static class Response {
        private Long id;
        private Long userId;
        private String userNickname;
        private Integer rating;
        private String content;
        private List<String> imageUrls;

        @JsonFormat(pattern = "yyyy.MM.dd")
        private LocalDateTime createdAt;

        private Integer commentCount;

        public static Response from(Review review) {
            return Response.builder()
                    .id(review.getId())
                    .userId(review.getUser().getId())
                    .userNickname(review.getUser().getNickname())
                    .rating(review.getRating())
                    .content(review.getContent())
                    .imageUrls(review.getImageUrls())
                    .createdAt(review.getCreatedAt())
                    .commentCount(review.getComments().size())
                    .build();
        }
    }

    // 리뷰 상세 응답 (댓글 포함)
    @Getter
    @Builder
    public static class DetailResponse {
        private Long id;
        private Long userId;
        private String userNickname;
        private Integer rating;
        private String content;
        private List<String> imageUrls;

        @JsonFormat(pattern = "yyyy.MM.dd")
        private LocalDateTime createdAt;

        private List<CommentDto.Response> comments;

        public static DetailResponse from(Review review) {
            return DetailResponse.builder()
                    .id(review.getId())
                    .userId(review.getUser().getId())
                    .userNickname(review.getUser().getNickname())
                    .rating(review.getRating())
                    .content(review.getContent())
                    .imageUrls(review.getImageUrls())
                    .createdAt(review.getCreatedAt())
                    .comments(review.getComments().stream()
                            .filter(c -> !c.getIsDeleted())
                            .map(CommentDto.Response::from)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    // 리뷰 목록 응답
    @Getter
    @Builder
    public static class ListResponse {
        private List<Response> reviews;
        private Integer totalPages;
        private Long totalElements;
        private Integer currentPage;
        private Integer pageSize;
    }
}
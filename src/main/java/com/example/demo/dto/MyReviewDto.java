package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 내가 작성한 리뷰 정보 DTO
 */
@Getter
@AllArgsConstructor
public class MyReviewDto {
    private Long reviewId;           // 리뷰 ID
    private Integer rating;          // 별점
    private String content;          // 리뷰 내용
    private LocalDateTime createdAt; // 작성 일시
    private String marketName;       // 시장 이름
}
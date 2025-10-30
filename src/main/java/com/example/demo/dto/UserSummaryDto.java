package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSummaryDto {
    private Long id;              // 사용자 ID
    private String name;          // 사용자 이름
    private String email;         // 이메일
    private Long totalPoints;     // 누적 포인트 합계
    private Long reviewCount;     // 작성한 리뷰 개수
}

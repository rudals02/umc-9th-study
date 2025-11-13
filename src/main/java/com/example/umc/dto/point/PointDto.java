package com.example.umc.dto.point;

import com.example.umc.domain.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class PointDto {

    // 포인트 내역 응답
    @Getter
    @Builder
    public static class Response {
        private Long id;
        private Integer amount;
        private String type;
        private String description;

        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        private LocalDateTime createdAt;

        public static Response from(Point point) {
            return Response.builder()
                    .id(point.getId())
                    .amount(point.getAmount())
                    .type(point.getType().getDescription())
                    .description(point.getDescription())
                    .createdAt(point.getCreatedAt())
                    .build();
        }
    }

    // 포인트 내역 목록 응답
    @Getter
    @Builder
    public static class ListResponse {
        private List<Response> points;
        private Integer totalPages;
        private Long totalElements;
        private Integer currentPage;
        private Integer pageSize;
        private Integer totalPoints; // 현재 보유 포인트
    }

    // 포인트 요약 응답
    @Getter
    @Builder
    public static class SummaryResponse {
        private Integer totalPoints; // 현재 보유 포인트
        private Integer totalEarned; // 총 적립
        private Integer totalUsed; // 총 사용
    }
}
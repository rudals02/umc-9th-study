package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AvailableMissionDto {
    private Long marketId;               // 시장 ID
    private String marketName;           // 시장 이름
    private String address;              // 시장 주소
    private Long missionId;              // 미션 ID
    private String status;               // 미션 상태
    private LocalDateTime startTime;     // 시작 시간
    private LocalDateTime completedAt;   // 완료 시간
    private Long earnedPoints;           // 획득 포인트
}
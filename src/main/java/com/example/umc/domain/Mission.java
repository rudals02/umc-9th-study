package com.example.umc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "missions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionType type;

    @Column(nullable = false)
    private Integer targetCount; // 목표 횟수

    @Column(nullable = false)
    private Integer rewardPoints; // 보상 포인트

    @Column(name = "days_valid")
    private Integer daysValid; // D-7 등 유효 기간

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum MissionType {
        DAILY("일일 미션"),
        WEEKLY("주간 미션"),
        SPECIAL("특별 미션");

        private final String description;

        MissionType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
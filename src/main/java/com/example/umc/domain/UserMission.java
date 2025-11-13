package com.example.umc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_missions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "mission_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(nullable = false)
    @Builder.Default
    private Integer currentCount = 0; // 현재 진행 횟수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MissionStatus status = MissionStatus.IN_PROGRESS;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum MissionStatus {
        IN_PROGRESS("진행중"),
        COMPLETED("완료"),
        EXPIRED("종료됨");

        private final String description;

        MissionStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // 진행도 업데이트
    public void incrementProgress() {
        this.currentCount++;
        if (this.currentCount >= mission.getTargetCount()) {
            this.status = MissionStatus.COMPLETED;
            this.completedAt = LocalDateTime.now();
        }
    }

    // 미션 완료 처리
    public void complete() {
        this.status = MissionStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
}
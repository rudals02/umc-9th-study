package com.example.umc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "points")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer amount; // 포인트 변동량 (양수: 적립, 음수: 사용)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointType type;

    @Column(length = 200)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum PointType {
        MISSION_REWARD("미션 보상"),
        REVIEW_REWARD("리뷰 작성"),
        PURCHASE_USE("구매 사용"),
        ADMIN_ADJUST("관리자 조정");

        private final String description;

        PointType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
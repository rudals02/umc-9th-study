package com.example.umc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 30)
    private String nickname;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "total_points", nullable = false)
    @Builder.Default
    private Integer totalPoints = 0; // 보유 포인트

    @Column(name = "is_phone_verified", nullable = false)
    @Builder.Default
    private Boolean isPhoneVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Role {
        USER, ADMIN
    }

    // 포인트 적립
    public void addPoints(Integer points) {
        this.totalPoints += points;
    }

    // 포인트 사용
    public void usePoints(Integer points) {
        if (this.totalPoints < points) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.totalPoints -= points;
    }

    // 닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    // 휴대폰 인증
    public void verifyPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.isPhoneVerified = true;
    }
}
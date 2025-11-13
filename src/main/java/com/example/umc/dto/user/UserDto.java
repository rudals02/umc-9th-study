package com.example.umc.dto.user;

import com.example.umc.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class UserDto {

    // 마이페이지 응답
    @Getter
    @Builder
    public static class MyPageResponse {
        private Long id;
        private String email;
        private String nickname;
        private String phoneNumber;
        private Integer totalPoints;
        private Boolean isPhoneVerified;

        public static MyPageResponse from(User user) {
            return MyPageResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .phoneNumber(user.getPhoneNumber())
                    .totalPoints(user.getTotalPoints())
                    .isPhoneVerified(user.getIsPhoneVerified())
                    .build();
        }
    }

    // 닉네임 변경 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateNicknameRequest {

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(min = 2, max = 30, message = "닉네임은 2~30자 사이여야 합니다.")
        private String nickname;
    }

    // 휴대폰 인증 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneVerifyRequest {

        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        private String phoneNumber;

        @NotBlank(message = "인증번호는 필수입니다.")
        private String verificationCode;
    }
}
package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.SuccessCode;
import com.example.umc.dto.user.UserDto;
import com.example.umc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 마이페이지 조회
     * GET /api/users/me
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto.MyPageResponse>> getMyPage(
            @RequestAttribute("userId") Long userId) {

        UserDto.MyPageResponse response = userService.getMyPage(userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.SUCCESS, response));
    }

    /**
     * 닉네임 변경
     * PATCH /api/users/me/nickname
     */
    @PatchMapping("/me/nickname")
    public ResponseEntity<ApiResponse<UserDto.MyPageResponse>> updateNickname(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody UserDto.UpdateNicknameRequest request) {

        UserDto.MyPageResponse response = userService.updateNickname(userId, request);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.USER_PROFILE_UPDATE_SUCCESS, response));
    }

    /**
     * 휴대폰 인증
     * POST /api/users/me/verify-phone
     */
    @PostMapping("/me/verify-phone")
    public ResponseEntity<ApiResponse<UserDto.MyPageResponse>> verifyPhone(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody UserDto.PhoneVerifyRequest request) {

        UserDto.MyPageResponse response = userService.verifyPhone(userId, request);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.USER_PHONE_VERIFY_SUCCESS, response));
    }
}
package com.example.umc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    @Builder
    public ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // 성공 응답 (데이터 있음)
    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    // 성공 응답 (데이터 없음)
    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .build();
    }

    // 성공 응답 (커스텀 메시지)
    public static <T> ApiResponse<T> success(SuccessCode successCode, String customMessage, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(successCode.getCode())
                .message(customMessage)
                .data(data)
                .build();
    }
}
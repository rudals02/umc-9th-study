package com.example.umc.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private boolean success;
    private String code;
    private String message;
    private LocalDateTime timestamp;
    private List<FieldErrorDetail> errors;

    @Builder
    public ErrorResponse(String code, String message, List<FieldErrorDetail> errors) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors != null ? errors : new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(FieldErrorDetail.of(bindingResult))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldErrorDetail {
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldErrorDetail(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldErrorDetail> of(BindingResult bindingResult) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<FieldErrorDetail> errors = new ArrayList<>();

            for (FieldError error : fieldErrors) {
                errors.add(FieldErrorDetail.builder()
                        .field(error.getField())
                        .value(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
                        .reason(error.getDefaultMessage())
                        .build());
            }

            return errors;
        }
    }
}
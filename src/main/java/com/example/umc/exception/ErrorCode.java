package com.example.umc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common Errors (1000~1999)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력 값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 내부 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "잘못된 타입 값입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C005", "엔티티를 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "접근 권한이 없습니다."),

    // User Errors (2000~2999)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U002", "이미 사용중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "U003", "이미 사용중인 닉네임입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U004", "잘못된 비밀번호입니다."),
    PHONE_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "U005", "휴대폰 인증이 필요합니다."),
    INSUFFICIENT_POINTS(HttpStatus.BAD_REQUEST, "U006", "포인트가 부족합니다."),

    // Review Errors (3000~3999)
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "리뷰를 찾을 수 없습니다."),
    REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "R002", "이미 작성한 리뷰가 존재합니다."),
    UNAUTHORIZED_REVIEW_ACCESS(HttpStatus.FORBIDDEN, "R003", "리뷰에 대한 권한이 없습니다."),
    INVALID_RATING(HttpStatus.BAD_REQUEST, "R004", "별점은 1~5 사이의 값이어야 합니다."),

    // Comment Errors (4000~4999)
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CM001", "댓글을 찾을 수 없습니다."),
    UNAUTHORIZED_COMMENT_ACCESS(HttpStatus.FORBIDDEN, "CM002", "댓글에 대한 권한이 없습니다."),

    // Mission Errors (5000~5999)
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "미션을 찾을 수 없습니다."),
    MISSION_ALREADY_STARTED(HttpStatus.CONFLICT, "M002", "이미 시작한 미션입니다."),
    MISSION_NOT_STARTED(HttpStatus.BAD_REQUEST, "M003", "시작하지 않은 미션입니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.CONFLICT, "M004", "이미 완료한 미션입니다."),
    MISSION_EXPIRED(HttpStatus.BAD_REQUEST, "M005", "만료된 미션입니다."),

    // Point Errors (6000~6999)
    POINT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "포인트 내역을 찾을 수 없습니다."),
    INVALID_POINT_AMOUNT(HttpStatus.BAD_REQUEST, "P002", "잘못된 포인트 금액입니다."),

    // Auth Errors (7000~7999)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
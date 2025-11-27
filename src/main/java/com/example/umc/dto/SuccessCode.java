package com.example.umc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Common Success (200 OK)
    SUCCESS(HttpStatus.OK, "S001", "요청이 성공적으로 처리되었습니다."),

    // User Success
    USER_SIGNUP_SUCCESS(HttpStatus.CREATED, "U001", "회원가입이 완료되었습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "U002", "로그인이 완료되었습니다."),
    USER_PROFILE_UPDATE_SUCCESS(HttpStatus.OK, "U003", "프로필이 수정되었습니다."),
    USER_PHONE_VERIFY_SUCCESS(HttpStatus.OK, "U004", "휴대폰 인증이 완료되었습니다."),

    // Review Success
    REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, "R001", "리뷰가 작성되었습니다."),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "R002", "리뷰가 수정되었습니다."),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "R003", "리뷰가 삭제되었습니다."),
    REVIEW_FETCH_SUCCESS(HttpStatus.OK, "R004", "리뷰 조회가 완료되었습니다."),

    // Comment Success
    COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "CM001", "댓글이 작성되었습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "CM002", "댓글이 삭제되었습니다."),

    // Mission Success
    MISSION_START_SUCCESS(HttpStatus.CREATED, "M001", "미션이 시작되었습니다."),
    MISSION_CHALLENGE_SUCCESS(HttpStatus.CREATED, "M002", "미션 도전이 시작되었습니다."),
    MISSION_PROGRESS_SUCCESS(HttpStatus.OK, "M003", "미션 진행도가 업데이트되었습니다."),
    MISSION_COMPLETE_SUCCESS(HttpStatus.OK, "M004", "미션이 완료되었습니다."),
    MISSION_FETCH_SUCCESS(HttpStatus.OK, "M005", "미션 조회가 완료되었습니다."),

    // Point Success
    POINT_EARN_SUCCESS(HttpStatus.OK, "P001", "포인트가 적립되었습니다."),
    POINT_USE_SUCCESS(HttpStatus.OK, "P002", "포인트가 사용되었습니다."),
    POINT_HISTORY_FETCH_SUCCESS(HttpStatus.OK, "P003", "포인트 내역 조회가 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
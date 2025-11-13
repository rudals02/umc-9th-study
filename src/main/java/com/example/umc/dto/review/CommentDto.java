package com.example.umc.dto.review;

import com.example.umc.domain.ReviewComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDto {

    // 댓글 작성 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {

        @NotBlank(message = "댓글 내용은 필수입니다.")
        @Size(max = 500, message = "댓글은 최대 500자입니다.")
        private String content;
    }

    // 댓글 응답
    @Getter
    @Builder
    public static class Response {
        private Long id;
        private Long userId;
        private String userNickname;
        private String content;

        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        private LocalDateTime createdAt;

        public static Response from(ReviewComment comment) {
            return Response.builder()
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .userNickname(comment.getUser().getNickname())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .build();
        }
    }
}
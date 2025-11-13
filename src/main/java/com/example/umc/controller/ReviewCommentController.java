package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.SuccessCode;
import com.example.umc.dto.review.CommentDto;
import com.example.umc.service.ReviewCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews/{reviewId}/comments")
@RequiredArgsConstructor
public class ReviewCommentController {

    private final ReviewCommentService commentService;

    /**
     * 댓글 작성
     * POST /api/reviews/{reviewId}/comments
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommentDto.Response>> createComment(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody CommentDto.CreateRequest request) {

        CommentDto.Response response = commentService.createComment(userId, reviewId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessCode.COMMENT_CREATE_SUCCESS, response));
    }

    /**
     * 댓글 삭제
     * DELETE /api/reviews/{reviewId}/comments/{commentId}
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long reviewId,
            @PathVariable Long commentId) {

        commentService.deleteComment(userId, commentId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COMMENT_DELETE_SUCCESS));
    }
}
package com.example.umc.service;

import com.example.umc.domain.Review;
import com.example.umc.domain.ReviewComment;
import com.example.umc.domain.User;
import com.example.umc.dto.review.CommentDto;
import com.example.umc.exception.BusinessException;
import com.example.umc.exception.ErrorCode;
import com.example.umc.repository.ReviewCommentRepository;
import com.example.umc.repository.ReviewRepository;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentService {

    private final ReviewCommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public CommentDto.Response createComment(Long userId, Long reviewId, CommentDto.CreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));


        ReviewComment comment = ReviewComment.builder()
                .user(user)
                .content(request.getContent())
                .build();


        commentRepository.save(comment);

        log.info("댓글 작성 완료: userId={}, reviewId={}, commentId={}", userId, reviewId, comment.getId());

        return CommentDto.Response.from(comment);
    }

    /**
     * 댓글 삭제 (소프트 삭제)
     */
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        ReviewComment comment = commentRepository.findByIdAndNotDeleted(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        // 작성자 본인 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_COMMENT_ACCESS);
        }

        comment.softDelete();

        log.info("댓글 삭제 완료: userId={}, commentId={}", userId, commentId);
    }
}
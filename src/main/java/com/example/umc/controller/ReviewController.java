package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.review.ReviewDto;
import com.example.umc.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
            summary = "내가 작성한 리뷰 목록 조회",
            description = "현재 로그인한 사용자가 작성한 리뷰 목록을 페이징하여 조회합니다. 한 페이지에 10개씩 조회됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "리뷰 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewDto.ReviewPageResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (page는 1 이상의 값이어야 함)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음"
            )
    })
    @GetMapping("/my")
    public ApiResponse<ReviewDto.ReviewPageResponse> getMyReviews(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @RequestParam Long userId,

            @Parameter(description = "페이지 번호 (1부터 시작)", required = true, example = "1")
            @RequestParam Integer page
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        ReviewDto.ReviewPageResponse response = reviewService.getMyReviews(userId, page - 1);
        return ApiResponse.of("내가 작성한 리뷰 목록을 조회했습니다.", response);
    }
}
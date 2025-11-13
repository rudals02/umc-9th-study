package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.SuccessCode;
import com.example.umc.dto.point.PointDto;
import com.example.umc.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     * 포인트 내역 조회
     * GET /api/points/history?page=0&size=20
     */
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<PointDto.ListResponse>> getPointHistory(
            @RequestAttribute("userId") Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        PointDto.ListResponse response = pointService.getPointHistory(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.POINT_HISTORY_FETCH_SUCCESS, response));
    }

    /**
     * 포인트 요약 조회
     * GET /api/points/summary
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<PointDto.SummaryResponse>> getPointSummary(
            @RequestAttribute("userId") Long userId) {

        PointDto.SummaryResponse response = pointService.getPointSummary(userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.SUCCESS, response));
    }
}
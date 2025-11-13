package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.SuccessCode;
import com.example.umc.dto.mission.MissionDto;
import com.example.umc.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    /**
     * 활성 미션 목록 조회
     * GET /api/missions
     */
    @GetMapping
    public ResponseEntity<ApiResponse<MissionDto.ListResponse>> getActiveMissions() {
        MissionDto.ListResponse response = missionService.getActiveMissions();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MISSION_FETCH_SUCCESS, response));
    }

    /**
     * 내 미션 목록 조회
     * GET /api/missions/my
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<MissionDto.UserMissionListResponse>> getMyMissions(
            @RequestAttribute("userId") Long userId) {

        MissionDto.UserMissionListResponse response = missionService.getMyMissions(userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MISSION_FETCH_SUCCESS, response));
    }

    /**
     * 미션 시작
     * POST /api/missions/{missionId}/start
     */
    @PostMapping("/{missionId}/start")
    public ResponseEntity<ApiResponse<MissionDto.UserMissionResponse>> startMission(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long missionId) {

        MissionDto.UserMissionResponse response = missionService.startMission(userId, missionId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessCode.MISSION_START_SUCCESS, response));
    }

    /**
     * 미션 진행도 업데이트
     * POST /api/missions/{missionId}/progress
     */
    @PostMapping("/{missionId}/progress")
    public ResponseEntity<ApiResponse<MissionDto.ProgressResponse>> updateMissionProgress(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long missionId) {

        MissionDto.ProgressResponse response = missionService.updateMissionProgress(userId, missionId);

        // 미션 완료 여부에 따라 다른 메시지 반환
        SuccessCode successCode = response.getIsCompleted()
                ? SuccessCode.MISSION_COMPLETE_SUCCESS
                : SuccessCode.MISSION_PROGRESS_SUCCESS;

        return ResponseEntity.ok(ApiResponse.success(successCode, response));
    }
}
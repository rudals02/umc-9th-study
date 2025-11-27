package com.example.umc.controller;

import com.example.umc.dto.ApiResponse;
import com.example.umc.dto.mission.MissionDto;
import com.example.umc.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "미션", description = "미션 관련 API")
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
@Validated
public class MissionController {

    private final MissionService missionService;

    @Operation(
            summary = "특정 가게의 미션 목록 조회",
            description = "특정 가게의 활성화된 미션 목록을 페이징하여 조회합니다. 한 페이지에 10개씩 조회됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "미션 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = MissionDto.MissionPageResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (page는 1 이상의 값이어야 함)"
            )
    })
    @GetMapping("/store")
    public ApiResponse<MissionDto.MissionPageResponse> getStoreMissions(
            @Parameter(description = "페이지 번호 (1부터 시작)", required = true, example = "1")
            @RequestParam Integer page
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        MissionDto.MissionPageResponse response = missionService.getStoreMissions(page - 1);
        return ApiResponse.of("가게의 미션 목록을 조회했습니다.", response);
    }

    @Operation(
            summary = "내가 진행중인 미션 목록 조회",
            description = "현재 로그인한 사용자가 진행중인 미션 목록을 페이징하여 조회합니다. 한 페이지에 10개씩 조회됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "진행중인 미션 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = MissionDto.UserMissionPageResponse.class))
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
    @GetMapping("/my/in-progress")
    public ApiResponse<MissionDto.UserMissionPageResponse> getMyInProgressMissions(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @RequestParam Long userId,

            @Parameter(description = "페이지 번호 (1부터 시작)", required = true, example = "1")
            @RequestParam Integer page
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        MissionDto.UserMissionPageResponse response = missionService.getMyInProgressMissions(userId, page - 1);
        return ApiResponse.of("내가 진행중인 미션 목록을 조회했습니다.", response);
    }
}
package com.example.umc.dto.mission;

import com.example.umc.domain.Mission;
import com.example.umc.domain.UserMission;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class MissionDto {

    // 미션 응답
    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String type;
        private Integer targetCount;
        private Integer rewardPoints;
        private Integer daysValid; // D-7 등

        public static Response from(Mission mission) {
            return Response.builder()
                    .id(mission.getId())
                    .title(mission.getTitle())
                    .description(mission.getDescription())
                    .type(mission.getType().getDescription())
                    .targetCount(mission.getTargetCount())
                    .rewardPoints(mission.getRewardPoints())
                    .daysValid(mission.getDaysValid())
                    .build();
        }
    }

    // 사용자 미션 진행 상태 응답
    @Getter
    @Builder
    public static class UserMissionResponse {
        private Long id;
        private Long missionId;
        private String title;
        private String description;
        private String status; // 진행중, 종식됨
        private Integer currentCount;
        private Integer targetCount;
        private Integer rewardPoints;
        private Integer daysValid;

        @JsonFormat(pattern = "yyyy.MM.dd")
        private LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy.MM.dd")
        private LocalDateTime completedAt;

        public static UserMissionResponse from(UserMission userMission) {
            Mission mission = userMission.getMission();
            return UserMissionResponse.builder()
                    .id(userMission.getId())
                    .missionId(mission.getId())
                    .title(mission.getTitle())
                    .description(mission.getDescription())
                    .status(userMission.getStatus().getDescription())
                    .currentCount(userMission.getCurrentCount())
                    .targetCount(mission.getTargetCount())
                    .rewardPoints(mission.getRewardPoints())
                    .daysValid(mission.getDaysValid())
                    .createdAt(userMission.getCreatedAt())
                    .completedAt(userMission.getCompletedAt())
                    .build();
        }
    }

    // 미션 목록 응답
    @Getter
    @Builder
    public static class ListResponse {
        private List<Response> missions;
    }

    // 사용자 미션 목록 응답
    @Getter
    @Builder
    public static class UserMissionListResponse {
        private List<UserMissionResponse> missions;
        private Integer totalMissions;
        private Integer completedMissions;
        private Integer inProgressMissions;
    }

    // 미션 진행도 업데이트 응답
    @Getter
    @Builder
    public static class ProgressResponse {
        private Long userMissionId;
        private Integer currentCount;
        private Integer targetCount;
        private String status;
        private Boolean isCompleted;
        private Integer earnedPoints;

        public static ProgressResponse from(UserMission userMission, Integer earnedPoints) {
            return ProgressResponse.builder()
                    .userMissionId(userMission.getId())
                    .currentCount(userMission.getCurrentCount())
                    .targetCount(userMission.getMission().getTargetCount())
                    .status(userMission.getStatus().getDescription())
                    .isCompleted(userMission.getStatus() == UserMission.MissionStatus.COMPLETED)
                    .earnedPoints(earnedPoints)
                    .build();
        }
    }
}
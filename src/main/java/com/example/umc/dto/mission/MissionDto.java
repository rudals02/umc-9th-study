package com.example.umc.dto.mission;

import com.example.umc.domain.Mission;
import com.example.umc.domain.UserMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MissionDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionResponse {
        private Long id;
        private String title;
        private String description;
        private Mission.MissionType type;
        private Integer targetCount;
        private Integer rewardPoints;
        private Integer daysValid;
        private Boolean isActive;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPageResponse {
        private List<MissionResponse> missions;
        private Integer currentPage;
        private Integer totalPages;
        private Long totalElements;
        private Boolean hasNext;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserMissionResponse {
        private Long id;
        private Long missionId;
        private String title;
        private String description;
        private Mission.MissionType type;
        private Integer targetCount;
        private Integer currentCount;
        private Integer rewardPoints;
        private UserMission.MissionStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime completedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserMissionPageResponse {
        private List<UserMissionResponse> missions;
        private Integer currentPage;
        private Integer totalPages;
        private Long totalElements;
        private Boolean hasNext;
    }
}
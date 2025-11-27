package com.example.umc.service;

import com.example.umc.domain.Mission;
import com.example.umc.domain.User;
import com.example.umc.domain.UserMission;
import com.example.umc.dto.mission.MissionDto;
import com.example.umc.repository.MissionRepository;
import com.example.umc.repository.UserMissionRepository;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;

    public MissionDto.MissionPageResponse getStoreMissions(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Mission> missionPage = missionRepository.findActiveStoreMissions(pageable);

        List<MissionDto.MissionResponse> missions = missionPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return MissionDto.MissionPageResponse.builder()
                .missions(missions)
                .currentPage(page)
                .totalPages(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .hasNext(missionPage.hasNext())
                .build();
    }

    public MissionDto.UserMissionPageResponse getMyInProgressMissions(Long userId, Integer page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(page, 10);
        Page<UserMission> userMissionPage = userMissionRepository.findInProgressMissionsByUser(user, pageable);

        List<MissionDto.UserMissionResponse> missions = userMissionPage.getContent().stream()
                .map(this::convertToUserMissionResponse)
                .collect(Collectors.toList());

        return MissionDto.UserMissionPageResponse.builder()
                .missions(missions)
                .currentPage(page)
                .totalPages(userMissionPage.getTotalPages())
                .totalElements(userMissionPage.getTotalElements())
                .hasNext(userMissionPage.hasNext())
                .build();
    }

    private MissionDto.MissionResponse convertToResponse(Mission mission) {
        return MissionDto.MissionResponse.builder()
                .id(mission.getId())
                .title(mission.getTitle())
                .description(mission.getDescription())
                .type(mission.getType())
                .targetCount(mission.getTargetCount())
                .rewardPoints(mission.getRewardPoints())
                .daysValid(mission.getDaysValid())
                .isActive(mission.getIsActive())
                .build();
    }

    private MissionDto.UserMissionResponse convertToUserMissionResponse(UserMission userMission) {
        Mission mission = userMission.getMission();
        return MissionDto.UserMissionResponse.builder()
                .id(userMission.getId())
                .missionId(mission.getId())
                .title(mission.getTitle())
                .description(mission.getDescription())
                .type(mission.getType())
                .targetCount(mission.getTargetCount())
                .currentCount(userMission.getCurrentCount())
                .rewardPoints(mission.getRewardPoints())
                .status(userMission.getStatus())
                .createdAt(userMission.getCreatedAt())
                .completedAt(userMission.getCompletedAt())
                .build();
    }
}
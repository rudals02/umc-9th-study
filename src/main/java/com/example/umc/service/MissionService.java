package com.example.umc.service;

import com.example.umc.domain.Mission;
import com.example.umc.domain.Point;
import com.example.umc.domain.User;
import com.example.umc.domain.UserMission;
import com.example.umc.dto.mission.MissionDto;
import com.example.umc.exception.BusinessException;
import com.example.umc.exception.ErrorCode;
import com.example.umc.repository.MissionRepository;
import com.example.umc.repository.PointRepository;
import com.example.umc.repository.UserMissionRepository;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    /**
     * 활성 미션 목록 조회
     */
    public MissionDto.ListResponse getActiveMissions() {
        List<Mission> missions = missionRepository.findAllActive();

        return MissionDto.ListResponse.builder()
                .missions(missions.stream()
                        .map(MissionDto.Response::from)
                        .toList())
                .build();
    }

    /**
     * 내 미션 목록 조회
     */
    public MissionDto.UserMissionListResponse getMyMissions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        List<UserMission> userMissions = userMissionRepository.findAllByUser(user);

        Long completedCount = userMissionRepository.countByUserAndStatus(user, UserMission.MissionStatus.COMPLETED);
        Long inProgressCount = userMissionRepository.countByUserAndStatus(user, UserMission.MissionStatus.IN_PROGRESS);

        return MissionDto.UserMissionListResponse.builder()
                .missions(userMissions.stream()
                        .map(MissionDto.UserMissionResponse::from)
                        .toList())
                .totalMissions(userMissions.size())
                .completedMissions(completedCount.intValue())
                .inProgressMissions(inProgressCount.intValue())
                .build();
    }

    /**
     * 미션 시작
     */
    @Transactional
    public MissionDto.UserMissionResponse startMission(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Mission mission = missionRepository.findByIdAndActive(missionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSION_NOT_FOUND));

        // 이미 시작한 미션인지 확인
        if (userMissionRepository.existsByUserAndMission(user, mission)) {
            throw new BusinessException(ErrorCode.MISSION_ALREADY_STARTED);
        }

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .build();

        userMissionRepository.save(userMission);

        log.info("미션 시작: userId={}, missionId={}", userId, missionId);

        return MissionDto.UserMissionResponse.from(userMission);
    }

    /**
     * 미션 진행도 업데이트
     */
    @Transactional
    public MissionDto.ProgressResponse updateMissionProgress(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Mission mission = missionRepository.findByIdAndActive(missionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSION_NOT_FOUND));

        UserMission userMission = userMissionRepository.findByUserAndMission(user, mission)
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSION_NOT_STARTED));

        // 이미 완료된 미션인지 확인
        if (userMission.getStatus() == UserMission.MissionStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.MISSION_ALREADY_COMPLETED);
        }

        // 진행도 업데이트
        userMission.incrementProgress();

        Integer earnedPoints = 0;

        // 미션 완료 시 포인트 지급
        if (userMission.getStatus() == UserMission.MissionStatus.COMPLETED) {
            earnedPoints = mission.getRewardPoints();
            user.addPoints(earnedPoints);

            Point point = Point.builder()
                    .user(user)
                    .amount(earnedPoints)
                    .type(Point.PointType.MISSION_REWARD)
                    .description(mission.getTitle() + " 미션 완료")
                    .build();
            pointRepository.save(point);

            log.info("미션 완료: userId={}, missionId={}, points={}", userId, missionId, earnedPoints);
        } else {
            log.info("미션 진행도 업데이트: userId={}, missionId={}, progress={}/{}",
                    userId, missionId, userMission.getCurrentCount(), mission.getTargetCount());
        }

        return MissionDto.ProgressResponse.from(userMission, earnedPoints);
    }
}
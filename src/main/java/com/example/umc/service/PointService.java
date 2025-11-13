package com.example.umc.service;

import com.example.umc.domain.Point;
import com.example.umc.domain.User;
import com.example.umc.dto.point.PointDto;
import com.example.umc.exception.BusinessException;
import com.example.umc.exception.ErrorCode;
import com.example.umc.repository.PointRepository;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    /**
     * 포인트 내역 조회
     */
    public PointDto.ListResponse getPointHistory(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Page<Point> pointPage = pointRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        return PointDto.ListResponse.builder()
                .points(pointPage.getContent().stream()
                        .map(PointDto.Response::from)
                        .toList())
                .totalPages(pointPage.getTotalPages())
                .totalElements(pointPage.getTotalElements())
                .currentPage(pointPage.getNumber())
                .pageSize(pointPage.getSize())
                .totalPoints(user.getTotalPoints())
                .build();
    }

    /**
     * 포인트 요약 조회
     */
    public PointDto.SummaryResponse getPointSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Integer totalEarned = pointRepository.getTotalEarnedPoints(user);
        Integer totalUsed = Math.abs(pointRepository.getTotalUsedPoints(user));

        return PointDto.SummaryResponse.builder()
                .totalPoints(user.getTotalPoints())
                .totalEarned(totalEarned)
                .totalUsed(totalUsed)
                .build();
    }

    /**
     * 포인트 사용
     */
    @Transactional
    public void usePoints(Long userId, Integer amount, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (amount <= 0) {
            throw new BusinessException(ErrorCode.INVALID_POINT_AMOUNT);
        }

        try {
            user.usePoints(amount);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_POINTS);
        }

        Point point = Point.builder()
                .user(user)
                .amount(-amount)
                .type(Point.PointType.PURCHASE_USE)
                .description(description)
                .build();

        pointRepository.save(point);

        log.info("포인트 사용: userId={}, amount={}, description={}", userId, amount, description);
    }
}
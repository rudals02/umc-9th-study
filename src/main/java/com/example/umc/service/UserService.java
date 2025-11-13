package com.example.umc.service;

import com.example.umc.domain.User;
import com.example.umc.dto.user.UserDto;
import com.example.umc.exception.BusinessException;
import com.example.umc.exception.ErrorCode;
import com.example.umc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 마이페이지 조회
     */
    public UserDto.MyPageResponse getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserDto.MyPageResponse.from(user);
    }

    /**
     * 닉네임 변경
     */
    @Transactional
    public UserDto.MyPageResponse updateNickname(Long userId, UserDto.UpdateNicknameRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
        }

        user.updateNickname(request.getNickname());

        log.info("닉네임 변경 완료: userId={}, newNickname={}", userId, request.getNickname());

        return UserDto.MyPageResponse.from(user);
    }

    /**
     * 휴대폰 인증
     */
    @Transactional
    public UserDto.MyPageResponse verifyPhone(Long userId, UserDto.PhoneVerifyRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // TODO: 실제 인증번호 검증 로직 필요

        user.verifyPhone(request.getPhoneNumber());

        log.info("휴대폰 인증 완료: userId={}, phoneNumber={}", userId, request.getPhoneNumber());

        return UserDto.MyPageResponse.from(user);
    }
}
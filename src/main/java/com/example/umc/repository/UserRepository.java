package com.example.umc.repository;

import com.example.umc.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(@NotBlank(message = "닉네임은 필수입니다.") @Size(min = 2, max = 30, message = "닉네임은 2~30자 사이여야 합니다.") String nickname);
}
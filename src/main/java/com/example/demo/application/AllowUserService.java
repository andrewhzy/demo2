package com.example.demo.application;

import com.example.demo.application.dto.AllowUserRequest;
import com.example.demo.application.dto.AllowUserResponse;
import com.example.demo.domain.AllowedUser;
import com.example.demo.infrastructure.AllowedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AllowUserService {

    private final AllowedUserRepository repository;

    public AllowUserResponse allowUser(AllowUserRequest request) {
        AllowedUser allowedUser = AllowedUser.builder()
                .id(UUID.randomUUID().toString())
                .allowedUserId(request.getUserId())
                .allowedAt(Instant.now())
                .allowedBy(request.getAllowedBy())
                .allowReason(request.getAllowReason())
                .build();

        AllowedUser saved = repository.save(allowedUser);

        return toResponse(saved);
    }

    @Transactional
    public void removeAllowedUser(String allowedUserId) {
        repository.deleteByAllowedUserId(allowedUserId);
    }

    private AllowUserResponse toResponse(AllowedUser allowedUser) {
        return AllowUserResponse.builder()
                .id(allowedUser.getId())
                .userId(allowedUser.getAllowedUserId())
                .allowedAt(allowedUser.getAllowedAt())
                .allowedBy(allowedUser.getAllowedBy())
                .allowReason(allowedUser.getAllowReason())
                .build();
    }
}

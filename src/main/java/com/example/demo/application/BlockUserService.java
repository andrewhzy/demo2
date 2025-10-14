package com.example.demo.application;

import com.example.demo.application.dto.BlockUserRequest;
import com.example.demo.application.dto.BlockUserResponse;
import com.example.demo.domain.BlockedUser;
import com.example.demo.infrastructure.BlockedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockUserService {

    private final BlockedUserRepository repository;

    public BlockUserResponse blockUser(BlockUserRequest request) {
        BlockedUser blockedUser = BlockedUser.builder()
                .id(UUID.randomUUID().toString())
                .blockedUserId(request.getUserId())
                .createdAt(Instant.now())
                .blockedBy(request.getBlockedBy())
                .blockReason(request.getBlockReason())
                .build();

        BlockedUser saved = repository.save(blockedUser);

        return toResponse(saved);
    }

    public void unblockUser(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public void unblockUserByUserId(String blockedUserId) {
        repository.deleteByBlockedUserId(blockedUserId);
    }

    private BlockUserResponse toResponse(BlockedUser blockedUser) {
        return BlockUserResponse.builder()
                .id(blockedUser.getId())
                .userId(blockedUser.getBlockedUserId())
                .blockedAt(blockedUser.getCreatedAt())
                .blockedBy(blockedUser.getBlockedBy())
                .blockReason(blockedUser.getBlockReason())
                .build();
    }
}

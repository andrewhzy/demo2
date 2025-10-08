package com.example.demo.application;

import com.example.demo.application.dto.BlockUserRequest;
import com.example.demo.application.dto.BlockUserResponse;
import com.example.demo.domain.BlockedUser;
import com.example.demo.infrastructure.BlockedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockUserService {

    private final BlockedUserRepository repository;

    public BlockUserResponse blockUser(BlockUserRequest request) {
        BlockedUser blockedUser = BlockedUser.builder()
                .id(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .blockedAt(Instant.now())
                .blockedBy(request.getBlockedBy())
                .blockReason(request.getBlockReason())
                .build();

        BlockedUser saved = repository.save(blockedUser);

        return toResponse(saved);
    }

    public void unblockUser(String id) {
        repository.deleteById(id);
    }

    private BlockUserResponse toResponse(BlockedUser blockedUser) {
        return BlockUserResponse.builder()
                .id(blockedUser.getId())
                .userId(blockedUser.getUserId())
                .blockedAt(blockedUser.getBlockedAt())
                .blockedBy(blockedUser.getBlockedBy())
                .blockReason(blockedUser.getBlockReason())
                .build();
    }
}

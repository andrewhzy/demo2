package com.example.demo.application;

import com.example.demo.application.dto.BlockUserRequest;
import com.example.demo.application.dto.BlockUserResponse;
import com.example.demo.domain.BlockedUser;
import com.example.demo.infrastructure.BlockedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockUserService {

    private final BlockedUserRepository repository;
    private final BlockedUserCacheService cacheService;

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

    public boolean isUserBlocked(String userId) {
        return cacheService.isUserBlocked(userId);
    }

    public java.util.List<BlockUserResponse> getAllBlockedUsers() {
        java.util.List<BlockUserResponse> result = new java.util.ArrayList<>();
        repository.findAll().forEach(blockedUser -> result.add(toResponse(blockedUser)));
        return result;
    }

    private BlockUserResponse toResponse(BlockedUser blockedUser) {
        return BlockUserResponse.builder()
                .id(blockedUser.getId())
                .userId(blockedUser.getBlockedUserId())
                .createdAt(blockedUser.getCreatedAt())
                .blockedBy(blockedUser.getBlockedBy())
                .blockReason(blockedUser.getBlockReason())
                .build();
    }
}

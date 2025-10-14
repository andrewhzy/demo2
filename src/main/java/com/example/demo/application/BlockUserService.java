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

    @Transactional
    public void unblockUserByUserId(String blockedUserId) {
        repository.deleteByBlockedUserId(blockedUserId);
    }

    /**
     * Check if a user is blocked using the cache for fast lookup.
     * @param userId the user ID to check
     * @return true if user is blocked, false otherwise
     */
    public boolean isUserBlocked(String userId) {
        return cacheService.isUserBlocked(userId);
    }

    /**
     * Get blocked user details from cache.
     * @param userId the user ID
     * @return BlockedUser if found, null otherwise
     */
    public BlockedUser getBlockedUserFromCache(String userId) {
        return cacheService.getBlockedUser(userId);
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

package com.example.demo.application;

import com.example.demo.application.dto.BlockUserRequest;
import com.example.demo.application.dto.BlockUserResponse;
import com.example.demo.domain.BlockedUser;
import com.example.demo.infrastructure.BlockedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BlockUserService {

    private final BlockedUserRepository repository;
    private final BlockedUserCacheService cacheService;

    public List<BlockUserResponse> blockUsers(List<BlockUserRequest> requests) {
        List<BlockedUser> blockedUsers = requests.stream()
                .map(request -> BlockedUser.builder()
                        .blockedUserId(request.getUserId())
                        .createdAt(Instant.now())
                        .blockedBy(request.getBlockedBy())
                        .blockReason(request.getBlockReason())
                        .build())
                .collect(Collectors.toList());

        Iterable<BlockedUser> savedUsers = repository.saveAll(blockedUsers);

        return StreamSupport.stream(savedUsers.spliterator(), false)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void unblockUsers(List<String> userIds) {
        repository.deleteByBlockedUserIdIn(userIds);
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
                .userId(blockedUser.getBlockedUserId())
                .createdAt(blockedUser.getCreatedAt())
                .blockedBy(blockedUser.getBlockedBy())
                .blockReason(blockedUser.getBlockReason())
                .build();
    }
}

package com.example.demo.application.blacklist;

import com.example.demo.application.blacklist.dto.BlockUserRequest;
import com.example.demo.application.blacklist.dto.BlockUserResponse;
import com.example.demo.domain.ServiceType;
import com.example.demo.domain.UserAccessRecord;
import com.example.demo.infrastructure.BlockedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockUserService {

    private final BlockedUserRepository repository;
    private final BlockedUserCacheService cacheService;

    public List<BlockUserResponse> blockUsers(List<BlockUserRequest> requests) {
        return requests.stream()
                .map(request -> UserAccessRecord.builder()
                        .serviceType(ServiceType.BLACKLIST)
                        .userId(request.getUserId())
                        .createdAt(Instant.now())
                        .createdBy(request.getCreatedBy())
                        .description(request.getDescription())
                        .build())
                .map(repository::save)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void unblockUsers(List<String> userIds) {
        userIds.forEach(userId -> repository.deleteByUserIdAndServiceType(userId, ServiceType.BLACKLIST));
    }

    public boolean isUserBlocked(String userId) {
        return cacheService.isUserBlocked(userId);
    }

    public java.util.List<BlockUserResponse> getAllBlockedUsers(List<String> userIds) {
        java.util.List<BlockUserResponse> result = new java.util.ArrayList<>();

        if (userIds == null || userIds.isEmpty()) {
            repository.findByServiceType(ServiceType.BLACKLIST).forEach(record -> result.add(toResponse(record)));
        } else {
            repository.findByUserIdInAndServiceType(userIds, ServiceType.BLACKLIST).forEach(record -> result.add(toResponse(record)));
        }

        return result;
    }

    private BlockUserResponse toResponse(UserAccessRecord record) {
        return BlockUserResponse.builder()
                .userId(record.getUserId())
                .createdAt(record.getCreatedAt())
                .createdBy(record.getCreatedBy())
                .description(record.getDescription())
                .build();
    }
}

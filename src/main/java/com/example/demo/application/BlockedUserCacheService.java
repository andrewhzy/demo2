package com.example.demo.application;

import com.example.demo.domain.BlockedUser;
import com.example.demo.infrastructure.BlockedUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "cache.blocked-users.enabled", havingValue = "true", matchIfMissing = true)
public class BlockedUserCacheService {

    private final BlockedUserRepository repository;
    private final ConcurrentHashMap<String, BlockedUser> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void initialize() {
        log.info("Initializing BlockedUser cache...");
        refreshCache();
        log.info("BlockedUser cache initialized with {} entries", cache.size());
    }

    @Scheduled(fixedRateString = "${cache.blocked-users.refresh-interval:30000}")
    public void refreshCache() {
        try {
            log.debug("Refreshing BlockedUser cache...");

            // Fetch all blocked users from Elasticsearch
            // Spring Data Elasticsearch automatically filters by entity type based on document structure
            Iterable<BlockedUser> allBlockedUsers = repository.findAll();

            // Clear and rebuild cache
            cache.clear();
            allBlockedUsers.forEach(user -> cache.put(user.getBlockedUserId(), user));

            log.debug("BlockedUser cache refreshed successfully with {} entries", cache.size());
        } catch (Exception e) {
            log.error("Error refreshing BlockedUser cache", e);
        }
    }

    /**
     * Check if a user is blocked.
     * @param userId the user ID to check
     * @return true if user is blocked, false otherwise
     */
    public boolean isUserBlocked(String userId) {
        return cache.containsKey(userId);
    }

    /**
     * Get blocked user details from cache.
     * @param userId the user ID
     * @return BlockedUser if found, null otherwise
     */
    public BlockedUser getBlockedUser(String userId) {
        return cache.get(userId);
    }

    /**
     * Get the current cache size.
     * @return number of blocked users in cache
     */
    public int getCacheSize() {
        return cache.size();
    }
}

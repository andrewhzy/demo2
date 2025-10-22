package com.example.demo.application.blacklist;

import com.example.demo.domain.BlockedUser;
import com.example.demo.infrastructure.BlockedUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockedUserCacheService {

    private final BlockedUserRepository repository;
    private final ConcurrentHashMap<String, BlockedUser> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeCache() {
        log.info("Initializing blocked users cache...");
        refreshCache();
        log.info("Blocked users cache initialized with {} users", cache.size());
    }

    @Scheduled(cron = "${blocked-users.cache.refresh-cron}")
    public void refreshCache() {
        log.info("Refreshing blocked users cache...");
        try {
            Iterable<BlockedUser> allUsers = repository.findAll();
            ConcurrentHashMap<String, BlockedUser> newCache = new ConcurrentHashMap<>();

            allUsers.forEach(user -> newCache.put(user.getBlockedUserId(), user));

            cache.clear();
            cache.putAll(newCache);

            log.info("Blocked users cache refreshed with {} users", cache.size());
        } catch (Exception e) {
            log.error("Error refreshing blocked users cache", e);
        }
    }

    public boolean isUserBlocked(String userId) {
        return cache.containsKey(userId);
    }
}

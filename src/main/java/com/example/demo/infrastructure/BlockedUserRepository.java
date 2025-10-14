package com.example.demo.infrastructure;

import com.example.demo.domain.BlockedUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockedUserRepository extends ElasticsearchRepository<BlockedUser, String> {

    Optional<BlockedUser> findByBlockedUserId(String blockedUserId);

    List<BlockedUser> findByBlockedUserIdIn(List<String> blockedUserIds);

    void deleteByBlockedUserIdIn(List<String> blockedUserIds);
}

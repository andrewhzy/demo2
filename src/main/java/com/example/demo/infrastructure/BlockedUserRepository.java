package com.example.demo.infrastructure;

import com.example.demo.domain.BlockedUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockedUserRepository extends ElasticsearchRepository<BlockedUser, String> {

    Optional<BlockedUser> findByUserId(String userId);
}

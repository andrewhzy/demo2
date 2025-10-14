package com.example.demo.infrastructure;

import com.example.demo.domain.AllowedUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllowedUserRepository extends ElasticsearchRepository<AllowedUser, String> {

    Optional<AllowedUser> findByAllowedUserId(String allowedUserId);

    void deleteByAllowedUserId(String allowedUserId);
}

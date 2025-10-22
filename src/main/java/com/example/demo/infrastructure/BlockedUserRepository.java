package com.example.demo.infrastructure;

import com.example.demo.domain.ServiceType;
import com.example.demo.domain.UserAccessRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockedUserRepository extends ElasticsearchRepository<UserAccessRecord, String> {

    List<UserAccessRecord> findByUserIdInAndServiceType(List<String> userIds, ServiceType serviceType);

    List<UserAccessRecord> findByServiceType(ServiceType serviceType);

    void deleteByUserIdAndServiceType(String userId, ServiceType serviceType);
}

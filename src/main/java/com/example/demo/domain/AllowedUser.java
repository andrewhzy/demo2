package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "glean-gateway-users-access")
public class AllowedUser {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String allowedUserId;

    @Field(type = FieldType.Date)
    private Instant allowedAt;

    @Field(type = FieldType.Keyword)
    private String allowedBy;

    @Field(type = FieldType.Text)
    private String allowReason;
}

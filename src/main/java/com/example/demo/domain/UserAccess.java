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
@Document(indexName = "#{@environment.getProperty('spring.elasticsearch.index-name')}", createIndex = false)
public class UserAccess {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    ServiceType serviceType;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Date)
    private Instant createdAt;

    @Field(type = FieldType.Keyword)
    private String createdBy;

    @Field(type = FieldType.Text)
    private String description;
}

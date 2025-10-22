package com.example.demo.application.blacklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserResponse {

    private String userId;
    private Instant createdAt;
    private String createdBy;
    private String description;
}

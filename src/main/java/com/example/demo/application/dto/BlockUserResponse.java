package com.example.demo.application.dto;

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

    private String blockedUserId;
    private Instant createdAt;
    private String blockedBy;
    private String blockReason;
}

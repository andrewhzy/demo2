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
public class AllowUserResponse {

    private String id;
    private String userId;
    private Instant allowedAt;
    private String allowedBy;
    private String allowReason;
}

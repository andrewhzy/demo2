package com.example.demo.application.blacklist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserRequest {

    private String blockedUserId;
    private String blockedBy;
    private String blockReason;
}

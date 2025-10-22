package com.example.demo.application.blacklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserRequest {

    private String userId;
    private String createdBy;
    private String description;
}

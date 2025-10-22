package com.example.demo.rest;

import com.example.demo.application.blacklist.BlockUserService;
import com.example.demo.application.blacklist.dto.BlockUserRequest;
import com.example.demo.application.blacklist.dto.BlockUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/blocked-users")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlockUserService blockUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<BlockUserResponse> blockUser(@RequestBody List<BlockUserRequest> requests) {
        return blockUserService.blockUsers(requests);
    }

    @GetMapping
    public java.util.List<BlockUserResponse> getAllBlockedUsers(@RequestBody(required = false) List<String> userIds) {
        if (userIds == null) {
            userIds = List.of();
        }
        return blockUserService.getAllBlockedUsers(userIds);
    }

    @PostMapping("/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblockUsers(@RequestBody List<String> userIds) {
        blockUserService.unblockUsers(userIds);
    }
}

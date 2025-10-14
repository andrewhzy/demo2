package com.example.demo.rest;

import com.example.demo.application.BlockUserService;
import com.example.demo.application.dto.BlockUserRequest;
import com.example.demo.application.dto.BlockUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/v1/blocked-users")
@RequiredArgsConstructor
public class BlockedUserController {

    private final BlockUserService blockUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockUserResponse blockUser(@RequestBody BlockUserRequest request) {
        return blockUserService.blockUser(request);
    }

    @DeleteMapping("/{blockedUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblockUser(@PathVariable String blockedUserId) {
        blockUserService.unblockUserByUserId(blockedUserId);
    }
}

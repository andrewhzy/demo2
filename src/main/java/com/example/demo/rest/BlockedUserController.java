package com.example.demo.rest;

import com.example.demo.application.BlockUserService;
import com.example.demo.application.dto.BlockUserRequest;
import com.example.demo.application.dto.BlockUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blocked-users")
@RequiredArgsConstructor
public class BlockedUserController {

    private final BlockUserService blockUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockUserResponse blockUser(@RequestBody BlockUserRequest request) {
        return blockUserService.blockUser(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblockUser(@PathVariable String id) {
        blockUserService.unblockUser(id);
    }
}

package com.example.demo.rest;

import com.example.demo.application.AllowUserService;
import com.example.demo.application.dto.AllowUserRequest;
import com.example.demo.application.dto.AllowUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/v1/allowed-users")
@RequiredArgsConstructor
public class AllowedUserController {

    private final AllowUserService allowUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AllowUserResponse allowUser(@RequestBody AllowUserRequest request) {
        return allowUserService.allowUser(request);
    }

    @DeleteMapping("/{allowedUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllowedUser(@PathVariable String allowedUserId) {
        allowUserService.removeAllowedUser(allowedUserId);
    }
}

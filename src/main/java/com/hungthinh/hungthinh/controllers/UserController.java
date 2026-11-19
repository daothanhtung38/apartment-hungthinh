package com.hungthinh.hungthinh.controllers;

import com.hungthinh.hungthinh.models.dtos.user.UserCreateRequest;
import com.hungthinh.hungthinh.models.dtos.user.UserGetsResponse;
import com.hungthinh.hungthinh.models.dtos.user.UserUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "User Management")
@RequiredArgsConstructor
public class UserController extends ApiBaseController {
    private final IUserService userService;

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResult<List<UserGetsResponse>>> getAllUser() {
        return executeApiResult(userService::getAllUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResult<UUID>> createUser(@RequestBody UserCreateRequest request) {
        return executeApiResult(() -> userService.createUser(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<String>> updateUser(@PathVariable("id") UUID userId, @RequestBody UserUpdateRequest request) {
        return executeApiResult(() -> userService.updateUser(userId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> deleteUser(@PathVariable("id") UUID userId) {
        return executeApiResult(() -> userService.deleteUser(userId));
    }
}

package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.dtos.user.UserCreateRequest;
import com.hungthinh.hungthinh.models.dtos.user.UserGetsResponse;
import com.hungthinh.hungthinh.models.dtos.user.UserUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    ApiResult<List<UserGetsResponse>> getAllUser();
    ApiResult<UUID> createUser(UserCreateRequest request);
    ApiResult<String> updateUser(UUID userId, UserUpdateRequest request);
    ApiResult<String> deleteUser(UUID userId);
}

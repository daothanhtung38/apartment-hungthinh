package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.models.dtos.auth.RegisterRequest;

public interface IAuthService {
    ApiResult<String> register(RegisterRequest request);
}

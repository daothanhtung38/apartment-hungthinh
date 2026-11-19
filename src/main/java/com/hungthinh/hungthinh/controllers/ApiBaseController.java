package com.hungthinh.hungthinh.controllers;

import java.util.function.Supplier;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.models.global.ApiResult;
import org.springframework.http.ResponseEntity;


public abstract class ApiBaseController {
    // Method mới cho việc xử lý ApiResult được tạo sẵn trong service
    protected <T> ResponseEntity<ApiResult<T>> executeApiResult(Supplier<ApiResult<T>> supplier) {
        try {
            ApiResult<T> result = supplier.get();
            return ResponseEntity.ok(result);
        } catch (UserMessageException ex) {
            return ResponseEntity.badRequest().body(ApiResult.fail(ex.getMessage()));
        } catch (Exception ex) {
            ApiResult<T> error = new ApiResult<>();
            error.setStatus(false);
            error.setUserMessage("Đã xảy ra lỗi.");
            error.setInternalMessage(ex.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}

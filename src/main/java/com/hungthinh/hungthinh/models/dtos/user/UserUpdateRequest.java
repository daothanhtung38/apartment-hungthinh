package com.hungthinh.hungthinh.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Vui long nhập tên đăng nhập")
    private String username;

    @NotBlank(message = "Vui lòng nhập vai trò")
    private String role;
}

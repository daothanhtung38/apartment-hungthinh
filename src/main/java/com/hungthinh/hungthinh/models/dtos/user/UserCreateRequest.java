package com.hungthinh.hungthinh.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "Vui long nhập tên đăng nhập")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;

    @NotBlank(message = "Vui lòng nhập vai trò")
    private String role;
}

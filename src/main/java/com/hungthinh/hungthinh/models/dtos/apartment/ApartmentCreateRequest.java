package com.hungthinh.hungthinh.models.dtos.apartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentCreateRequest {
    // Apartment
    @NotBlank(message = "Vui lòng nhập số căn hộ")
    private String apartmentNumber;

    @NotNull(message = "Vui lòng nhập tầng")
    private Integer floor;

    private String block;

    private BigDecimal area;

    // User
    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;

}

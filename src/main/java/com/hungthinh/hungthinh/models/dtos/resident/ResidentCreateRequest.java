package com.hungthinh.hungthinh.models.dtos.resident;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentCreateRequest {
    @NotBlank(message = "Vui lòng nhập họ và tên")
    private String fullName;

    private LocalDate dateOfBirth;

    private String email;

    @NotBlank(message = "Vui lòng nhập số điện thoại")
    private String phone;

    @NotBlank(message = "Vui lòng nhập quan hệ với chủ hộ")
    private String relation;
}

package com.hungthinh.hungthinh.models.dtos.resident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentGetResponse {
    private UUID apartmentId;

    private String apartmentNumber;

    private UUID id;

    private String fullName;

    private LocalDate dateOfBirth;

    private String email;

    private String phone;

    private String relation;

    private LocalDateTime lastModifiedTime;

    private LocalDateTime createTime;
}

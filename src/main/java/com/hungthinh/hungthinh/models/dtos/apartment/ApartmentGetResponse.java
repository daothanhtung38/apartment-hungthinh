package com.hungthinh.hungthinh.models.dtos.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentGetResponse {
    private UUID id;

    private String apartmentNumber;

    private Integer floor;

    private String block;

    private BigDecimal area;

    private String status;
}

package com.hungthinh.hungthinh.models.dtos.invoice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceUpdateRequest {
    private BigDecimal managementFee;
    private BigDecimal waterFee;
    private BigDecimal electricityFee;
    private BigDecimal parkingFee;
    private BigDecimal otherFee;
    private LocalDate dueDate;
    private String status;
}

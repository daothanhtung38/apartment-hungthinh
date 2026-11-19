package com.hungthinh.hungthinh.mapper;

import com.hungthinh.hungthinh.models.dtos.invoice.InvoicePaymentRequest;
import com.hungthinh.hungthinh.models.dtos.payment.PaymentGetsResponse;
import com.hungthinh.hungthinh.models.entities.bases.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentCode", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "paymentMethod",
    expression =  "java(com.hungthinh.hungthinh.models.entities.enums.PaymentMethod.valueOf(request.getPaymentMethod()))")
    Payment toEntity(InvoicePaymentRequest request);

    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    PaymentGetsResponse toResponse(Payment payment);

}

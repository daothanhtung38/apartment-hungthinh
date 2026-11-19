package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.dtos.invoice.InvoicePaymentRequest;
import com.hungthinh.hungthinh.models.dtos.payment.PaymentGetsResponse;
import com.hungthinh.hungthinh.models.global.ApiResult;

import java.util.List;

public interface IPaymentService {
    ApiResult<String> processInvoicePayment(String invoiceNumber, InvoicePaymentRequest request);

    ApiResult<List<PaymentGetsResponse>> getPaymentsByApartment(String apartmentNumber);

    ApiResult<List<PaymentGetsResponse>>getPaymentByInvoice(String invoiceNumber);

    ApiResult<PaymentGetsResponse> getPaymentByCode(String paymentCode);
}

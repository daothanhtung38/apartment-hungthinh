package com.hungthinh.hungthinh.controllers;

import com.hungthinh.hungthinh.models.dtos.invoice.InvoicePaymentRequest;
import com.hungthinh.hungthinh.models.dtos.payment.PaymentGetsResponse;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.services.interfaces.IPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Payment Manager")
public class PaymentController extends ApiBaseController {
    private final IPaymentService paymentService;

    @PreAuthorize("hashRole('Admin') or hashRole(''MANAGER)")
    @PostMapping("admin-accountant-resident/invoices/{invoiceNumber}/payment")
    public ResponseEntity<ApiResult<String>> processInvoicePayment(
            @PathVariable String invoiceNumber,
            @Valid @RequestBody InvoicePaymentRequest request
            ) {
        return executeApiResult(() -> paymentService.processInvoicePayment(invoiceNumber, request));
    }

    @PreAuthorize("hashRole('Admin') or hashRole(''MANAGER)")
    @PostMapping("admin-accountant-resident/payments/apartment/{apartmentNumber}")
    public ResponseEntity<ApiResult<List<PaymentGetsResponse>>> getPaymentByApartment(@PathVariable String apartmentNumber) {
        return executeApiResult(() -> paymentService.getPaymentsByApartment(apartmentNumber));
    }

    @GetMapping("admin-accountant-resident/payments/invoice/{invoiceNumber}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT') or hasRole('RESIDENT')")
    public ResponseEntity<ApiResult<List<PaymentGetsResponse>>> getPaymentInvoice(@PathVariable String invoiceNumber) {
        return executeApiResult(() -> paymentService.getPaymentByInvoice(invoiceNumber));
    }

    @GetMapping("admin-accountant-resident/payments/code/{paymentCode}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT') or hasRole('RESIDENT')")
    public ResponseEntity<ApiResult<PaymentGetsResponse>> getPaymentByCode(@PathVariable String paymentCode) {
        return executeApiResult(() -> paymentService.getPaymentByCode(paymentCode));
    }
}

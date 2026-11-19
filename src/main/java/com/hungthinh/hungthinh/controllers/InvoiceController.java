package com.hungthinh.hungthinh.controllers;

import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceBulkCreateRequest;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceCreateRequest;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceGetsResponse;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.repositories.InvoiceRepository;
import com.hungthinh.hungthinh.services.interfaces.IInvoiceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Invoice Manager")
@RequiredArgsConstructor
public class InvoiceController extends ApiBaseController {
    private final IInvoiceService invoiceService;

    @PostMapping("admin-accountant/invoices")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<ApiResult<UUID>> createInvoice(@Valid @RequestBody InvoiceCreateRequest request) {
        return executeApiResult(() -> invoiceService.createInvoice(request));
    }

    @PostMapping("admin-accountant/invoices/bulk")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<ApiResult<String>> createBulkInvoice(@Valid @RequestBody InvoiceBulkCreateRequest request) {
        return executeApiResult(() -> invoiceService.createBulkInvoice(request));
    }

    @PutMapping("admin-accountant/invoices/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<ApiResult<String>> updateInvoice(@PathVariable UUID invoiceId, @Valid @RequestBody InvoiceUpdateRequest request) {
        return executeApiResult(() -> invoiceService.updateInvoice(invoiceId, request));
    }

    @GetMapping("admin-accountant/invoices/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<ApiResult<List<InvoiceGetsResponse>>> getInvoiceWithFilter(
            @Parameter(description = "Trạng thái hóa đơn: PENDING, PAID, OVERDUE, CANCELLED") @RequestParam(required = false) String status,

            @Parameter(description = "Tháng (1-12)") @RequestParam(required = false) Integer month,

            @Parameter(description = "Năm") @RequestParam(required = false) Integer year) {
        return executeApiResult(() -> invoiceService.getInvoiceWithFilter(status, month, year));
    }

    @GetMapping("admin-accountant-resident/invoices/apartment/{apartmentNumber}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT') or hasRole('RESIDENT')")
    public ResponseEntity<ApiResult<List<InvoiceGetsResponse>>> getInvoicesByApartment(
            @PathVariable String apartmentNumber) {
        return executeApiResult(() -> invoiceService.getInvoiceByApartment(apartmentNumber));
    }
}

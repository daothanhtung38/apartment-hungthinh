package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceBulkCreateRequest;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceCreateRequest;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceGetsResponse;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;

import java.util.List;
import java.util.UUID;

public interface IInvoiceService {
    ApiResult<UUID> createInvoice(InvoiceCreateRequest request);

    ApiResult<String> createBulkInvoice(InvoiceBulkCreateRequest request);

    ApiResult<String> updateInvoice(UUID invoiceId, InvoiceUpdateRequest request);

    ApiResult<List<InvoiceGetsResponse>> getInvoiceWithFilter(String status, Integer month, Integer year);

    ApiResult<List<InvoiceGetsResponse>> getInvoiceByApartment(String apartmentNumber);
}

package com.hungthinh.hungthinh.services.implement;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceBulkCreateRequest;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceCreateRequest;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceGetsResponse;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoiceUpdateRequest;
import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import com.hungthinh.hungthinh.models.entities.bases.Invoice;
import com.hungthinh.hungthinh.models.entities.enums.InvoiceStatus;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.repositories.ApartmentRepository;
import com.hungthinh.hungthinh.repositories.InvoiceRepository;
import com.hungthinh.hungthinh.services.interfaces.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ApartmentRepository apartmentRepository;
    @Override
    @Transactional
    public ApiResult<UUID> createInvoice(InvoiceCreateRequest request) {
        Apartment apartment = apartmentRepository.findByApartmentNumber(request.getApartmentNumber())
                .orElseThrow(() -> new UserMessageException("Căn hộ không tồn tại"));

        // kiểm tra hóa đơn đã tồn tại cho căn hộ này chưa
        invoiceRepository.findByApartmentIdAndMonthAndYear(apartment.getId(),
                request.getMonth(), request.getYear())
                .ifPresent(existingInvoice -> {
                    throw new UserMessageException("Hóa đơn cho căn hộ " +
                            request.getApartmentNumber() + " tháng " +
                            request.getMonth() + "/" + request.getYear() + " đã tồn tại");
                });

        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceNumber(generateInvoiceNumber(apartment.getApartmentNumber(), request.getMonth(), request.getYear()));

        newInvoice.setApartment(apartment);
        newInvoice.setMonth(request.getMonth());
        newInvoice.setYear(request.getYear());
        newInvoice.setManagementFee(request.getManagementFee());
        newInvoice.setWaterFee(request.getWaterFee());
        newInvoice.setElectricityFee(request.getElectricityFee());
        newInvoice.setParkingFee(request.getParkingFee());
        newInvoice.setOtherFee(request.getOtherFee());

        // Tính tổng tiền
        BigDecimal totalAmount = calculateTotalAmount(
                request.getManagementFee(),
                request.getElectricityFee(),
                request.getParkingFee(),
                request.getWaterFee(),
                request.getOtherFee()
        );
        newInvoice.setTotalAmount(totalAmount);

        newInvoice.setDueDate(request.getDueDate() != null ? request.getDueDate() : LocalDate.now().plusDays(30));
        newInvoice.setStatus(InvoiceStatus.PENDING);

        invoiceRepository.save(newInvoice);

        return ApiResult.success(newInvoice.getId(), "Tạo hóa đơn thành công");
    }

    @Override
    @Transactional
    public ApiResult<String> createBulkInvoice(InvoiceBulkCreateRequest request) {
        List<Apartment> apartments = apartmentRepository.findAll();
        if (apartments.isEmpty()) {
            throw new UserMessageException("Không có căn hộ nào trong hệ thống");
        }

        List<Invoice> invoicesToSave = new ArrayList<>();
        int createdCount = 0;
        int skippedCount = 0;

        for (Apartment apartment : apartments) {
            // Kiểm tra nếu căn hộ này đã có hóa đơn trong tháng/năm thì bỏ qua
            if (invoiceRepository.findByApartmentIdAndMonthAndYear(apartment.getId(), request.getMonth(), request.getYear()).isPresent()) {
                skippedCount++;
                continue;
            }

            Invoice newInvoice = new Invoice();
            newInvoice.setInvoiceNumber(generateInvoiceNumber(apartment.getApartmentNumber(), request.getMonth(), request.getYear()));

            newInvoice.setApartment(apartment);
            newInvoice.setMonth(request.getMonth());
            newInvoice.setYear(request.getYear());
            newInvoice.setManagementFee(request.getManagementFee());
            newInvoice.setWaterFee(request.getWaterFee());
            newInvoice.setElectricityFee(request.getElectricityFee());
            newInvoice.setParkingFee(request.getParkingFee());
            newInvoice.setOtherFee(request.getOtherFee());

            BigDecimal totalAmount = calculateTotalAmount(
                    request.getManagementFee(),
                    request.getElectricityFee(),
                    request.getParkingFee(),
                    request.getWaterFee(),
                    request.getOtherFee()
            );
            newInvoice.setTotalAmount(totalAmount);

            newInvoice.setDueDate(request.getDueDate() != null ? request.getDueDate() : LocalDate.now().plusDays(30));
            newInvoice.setStatus(InvoiceStatus.PENDING);

            invoicesToSave.add(newInvoice);
            createdCount++;
        }

        if (!invoicesToSave.isEmpty()) {
            invoiceRepository.saveAll(invoicesToSave);
        }

        String message = String.format("Tạo hóa đơn hàng loạt thành công: %d hóa đơn được tạo, %d căn hộ đã có hóa đơn (bỏ qua)",
                createdCount, skippedCount);
        return ApiResult.success(null, message);
    }

    @Override
    public ApiResult<String> updateInvoice(UUID invoiceId, InvoiceUpdateRequest request) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new UserMessageException("Hóa đơn không tồn tại"));

        if (request.getManagementFee() != null) {
            invoice.setManagementFee(request.getManagementFee());
        }
        if (request.getElectricityFee() != null) {
            invoice.setElectricityFee(request.getElectricityFee());
        }
        if (request.getParkingFee() != null) {
            invoice.setParkingFee(request.getParkingFee());
        }
        if (request.getWaterFee() != null) {
            invoice.setWaterFee(request.getWaterFee());
        }
        if (request.getOtherFee() != null) {
            invoice.setOtherFee(request.getOtherFee());
        }

        // tính lại tổng tiền
        BigDecimal totalAmount = invoice.getManagementFee()
                .add(invoice.getElectricityFee())
                .add(invoice.getParkingFee())
                .add(invoice.getWaterFee())
                .add(invoice.getOtherFee());
        invoice.setTotalAmount(totalAmount);

        invoiceRepository.save(invoice);
        return ApiResult.success(null, "Cập nhật hóa đơn thành công");
    }

    @Override
    public ApiResult<List<InvoiceGetsResponse>> getInvoiceWithFilter(String status, Integer month, Integer year) {
        List<Invoice> invoices;

        // Xử lý logic filter theo từng case
        if (status != null && month != null && year != null) {
            // Có cả 3 tham số
            InvoiceStatus invoiceStatus = parseInvoiceStatus(status);
            invoices = invoiceRepository.findByStatusAndMonthAndYear(invoiceStatus, month, year);
        } else if (status != null && month != null) {
            // Có status và month
            InvoiceStatus invoiceStatus = parseInvoiceStatus(status);
            invoices = invoiceRepository.findByStatusAndMonth(invoiceStatus, month);
        } else if (status != null && year != null) {
            // Có status và year
            InvoiceStatus invoiceStatus = parseInvoiceStatus(status);
            invoices = invoiceRepository.findByStatusAndYear(invoiceStatus, year);
        } else if (month != null && year != null) {
            // Có month và year
            invoices = invoiceRepository.findByMonthAndYear(month, year);
        } else if (status != null) {
            // Chỉ có status
            InvoiceStatus invoiceStatus = parseInvoiceStatus(status);
            invoices = invoiceRepository.findByStatus(invoiceStatus);
        } else {
            // Không có filter nào, lấy tất cả
            invoices = invoiceRepository.findAll();
        }

        List<InvoiceGetsResponse> responseList = invoices.stream()
                .map(this::mapToInvoiceResponse)
                .toList();

        String message = buildFilterMessage(status, month, year);
        return ApiResult.success(responseList, message);
    }

    @Override
    public ApiResult<List<InvoiceGetsResponse>> getInvoiceByApartment(String apartmentNumber) {
        List<Invoice> invoices = invoiceRepository.findByApartmentApartmentNumber(apartmentNumber);
        List<InvoiceGetsResponse> responseList = invoices.stream()
                .map(this::mapToInvoiceResponse)
                .toList();

        return ApiResult.success(responseList, "Lấy danh sách hóa đơn theo căn hộ thành công");
    }

    private InvoiceStatus parseInvoiceStatus(String status) {
        try {
            return InvoiceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UserMessageException("Trạng thái hóa đơn không hợp lệ: " + status);
        }
    }

    private InvoiceGetsResponse mapToInvoiceResponse(Invoice invoice) {
        return new InvoiceGetsResponse(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getApartment().getApartmentNumber(),
                invoice.getApartment().getOwner() != null ? invoice.getApartment().getOwner().getUsername() : "",
                invoice.getMonth(),
                invoice.getYear(),
                invoice.getManagementFee(),
                invoice.getWaterFee(),
                invoice.getElectricityFee(),
                invoice.getParkingFee(),
                invoice.getOtherFee(),
                invoice.getTotalAmount(),
                invoice.getDueDate(),
                invoice.getStatus().getDisplayName());
    }

    private BigDecimal calculateTotalAmount(BigDecimal managementFee, BigDecimal electricityFee,
                                             BigDecimal parkingFee, BigDecimal waterFee, BigDecimal otherFee) {
        return (managementFee != null ? managementFee : BigDecimal.ZERO)
                .add(electricityFee != null ? electricityFee : BigDecimal.ZERO)
                .add(parkingFee != null ? parkingFee : BigDecimal.ZERO)
                .add(waterFee != null ? waterFee : BigDecimal.ZERO)
                .add(otherFee != null ? otherFee : BigDecimal.ZERO);
    }

    private String generateInvoiceNumber(String apartmentNumber, Integer month, Integer year) {
        return String.format("HD%s%02d%d", apartmentNumber, month, year);
    }

    private String buildFilterMessage(String status, Integer month, Integer year) {
        StringBuilder message = new StringBuilder("Lấy danh sách hóa đơn");

        if (status != null || month != null || year != null) {
            message.append(" với bộ lọc:");
            if (status != null)
                message.append(" trạng thái = ").append(status);
            if (month != null && year != null) {
                message.append(" thời gian = ").append(month).append("/").append(year);
            } else if (month != null) {
                message.append(" tháng = ").append(month);
            } else if (year != null) {
                message.append(" năm = ").append(year);
            }
        }

        message.append(" thành công");
        return message.toString();
    }
}

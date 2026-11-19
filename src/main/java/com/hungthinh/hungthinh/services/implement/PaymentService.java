package com.hungthinh.hungthinh.services.implement;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.mapper.PaymentMapper;
import com.hungthinh.hungthinh.models.dtos.invoice.InvoicePaymentRequest;
import com.hungthinh.hungthinh.models.dtos.payment.PaymentGetsResponse;
import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import com.hungthinh.hungthinh.models.entities.bases.Invoice;
import com.hungthinh.hungthinh.models.entities.bases.Payment;
import com.hungthinh.hungthinh.models.entities.enums.InvoiceStatus;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.repositories.InvoiceRepository;
import com.hungthinh.hungthinh.repositories.PaymentRepository;
import com.hungthinh.hungthinh.services.interfaces.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper paymentMapper;
    @Override
    public ApiResult<String> processInvoicePayment(String invoiceNumber, InvoicePaymentRequest request) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new UserMessageException("Hóa đơn không tồn tại"));

        // kiểm tra hóa đơn đã được thanh toán chưa
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new UserMessageException("Hóa đơn này đã đươc thanh toán");
        }

        if (request.getAmount().compareTo(invoice.getTotalAmount()) > 0) {
            throw new UserMessageException("Số tiền thanh toán không được nhỏ hơn 0");
        }

        // kiểm tra mã giao dịch đã được tồn tại chưa
        if (request.getTransactionId() != null && !request.getTransactionId().isEmpty()) {
            if (paymentRepository.existsByTransactionId(request.getTransactionId())) {
                throw new UserMessageException("Mã giao dịch đã tồn tại");
            }
        }

        Payment newPayment = paymentMapper.toEntity(request);

        paymentRepository.save(newPayment);

        // Cập nhật trạng thái hóa đơn nếu thanh toán đủ
        if (request.getAmount().compareTo(invoice.getTotalAmount()) == 0) {
            invoice.setStatus(InvoiceStatus.PAID);
            invoiceRepository.save(invoice);
        }
        
        return ApiResult.success(null, "Thanh toán hóa đơn" + invoiceNumber + "thành công");
    }

    @Override
    public ApiResult<List<PaymentGetsResponse>> getPaymentsByApartment(String apartmentNumber) {
        List<Payment> payments = paymentRepository.findByInvoiceApartmentApartmentNumber(apartmentNumber);
        List<PaymentGetsResponse> responsesList = payments.stream()
                .map(paymentMapper::toResponse)
                .toList();
        return ApiResult.success(responsesList, "Lấy danh sách thanh toán theo căn hộ thành công");
    }

    @Override
    public ApiResult<List<PaymentGetsResponse>> getPaymentByInvoice(String invoiceNumber) {
        List<Payment> payments = paymentRepository.findByInvoiceInvoiceNumber(invoiceNumber);
        List<PaymentGetsResponse> responseList = payments.stream()
                .map(paymentMapper::toResponse)
                .toList();
        return ApiResult.success(responseList, "Lấy danh sách thanh toán theo hóa đơn thành công");
    }

    @Override
    public ApiResult<PaymentGetsResponse> getPaymentByCode(String paymentCode) {
        Payment payments = paymentRepository.findByPaymentCode(paymentCode)
                .orElseThrow(() -> new UserMessageException("Thanh toán không tồn tại"));
        PaymentGetsResponse response = paymentMapper.toResponse(payments);
        return ApiResult.success(response, "Lấy thông tin thanh toán thành công");
    }
}

package com.hungthinh.hungthinh.repositories;

import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import com.hungthinh.hungthinh.models.entities.bases.Invoice;
import com.hungthinh.hungthinh.models.entities.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findByStatus(InvoiceStatus invoiceStatus);

    List<Invoice> findByApartmentId(UUID apartmentId);

    List<Invoice> findByMonthAndYear(Integer month, Integer year);

    List<Invoice> findByStatusAndMonth(InvoiceStatus invoiceStatus, Integer month);

    List<Invoice> findByStatusAndYear(InvoiceStatus invoiceStatus, Integer year);

    List<Invoice> findByStatusAndMonthAndYear(InvoiceStatus status, Integer month, Integer year);

    Optional<Invoice> findByApartmentIdAndMonthAndYear(UUID apartmentId, Integer month, Integer year);

    List<Invoice> findByApartmentApartmentNumber(String apartmentNumber);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}

package com.hungthinh.hungthinh.controllers;

import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentGetResponse;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.services.interfaces.IApartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/apartment")
@Tag(name = "Apartment Management")
@RequiredArgsConstructor
public class ApartmentController extends ApiBaseController {
    private final IApartmentService apartmentService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<ApiResult<List<ApartmentGetResponse>>> getAllApartment() {
        return executeApiResult(apartmentService::getApartments);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResult<UUID>> createApartment(@Valid @RequestBody ApartmentCreateRequest request) {
        return executeApiResult(() -> apartmentService.createApartments(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<String>> updateApartment(@PathVariable("id") UUID apartmentId, @Valid @RequestBody ApartmentUpdateRequest request) {
        return executeApiResult(() -> apartmentService.updateApartments(apartmentId, request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> deleteApartment(@PathVariable("id") UUID apartmentId) {
        return executeApiResult(() -> apartmentService.deleteApartment(apartmentId));
    }
}

package com.hungthinh.hungthinh.controllers;

import com.hungthinh.hungthinh.models.dtos.resident.ResidentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentGetResponse;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.services.interfaces.IResidentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Resident Manager")
public class ResidentController extends ApiBaseController {
    private final IResidentService residentService;

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/residents")
    public ResponseEntity<ApiResult<List<ResidentGetResponse>>> getAllResident() {
        return executeApiResult(residentService::getAllResident);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('RESIDENT')")
    @GetMapping("/apartments/{apartmentId}/residents")
    public ResponseEntity<ApiResult<List<ResidentGetResponse>>> getResidentByApartmentId(@PathVariable("apartmentId") UUID apartmentId) {
        return executeApiResult(() -> residentService.getResidentByApartmentId(apartmentId));
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping("/apartments/{apartmentId}/residents")
    public ResponseEntity<ApiResult<UUID>> createResident(@PathVariable("apartmentId") UUID apartmentId, @Valid @RequestBody ResidentCreateRequest request) {
        return executeApiResult(() -> residentService.createResident(apartmentId, request));
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PutMapping("/apartments/residents/{residentId}")
    public ResponseEntity<ApiResult<String>> updateResident(@PathVariable("residentId") UUID residentId, @Valid @RequestBody ResidentUpdateRequest request) {
        return executeApiResult(() -> residentService.updateResident(residentId, request));
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @DeleteMapping("/apartments/residents/{residentId}")
    public ResponseEntity<ApiResult<String>> deleteResident(@PathVariable("residentId") UUID residentId) {
        return executeApiResult(() -> residentService.deleteResident(residentId));
    }
}

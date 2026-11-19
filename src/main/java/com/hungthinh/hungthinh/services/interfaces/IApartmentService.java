package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentGetResponse;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;

import java.util.List;
import java.util.UUID;

public interface IApartmentService {
    ApiResult<List<ApartmentGetResponse>> getApartments();
    ApiResult<UUID> createApartments(ApartmentCreateRequest request);
    ApiResult<String> updateApartments(UUID apartmentId, ApartmentUpdateRequest request);
    ApiResult<String> deleteApartment(UUID apartmentId);
}

package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.dtos.resident.ResidentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentGetResponse;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentUpdateRequest;
import com.hungthinh.hungthinh.models.global.ApiResult;

import java.util.List;
import java.util.UUID;

public interface IResidentService {
    ApiResult<List<ResidentGetResponse>> getAllResident();

    ApiResult<List<ResidentGetResponse>> getResidentByApartmentId(UUID apartmentId);

    ApiResult<UUID> createResident(UUID apartmentId, ResidentCreateRequest request);

    ApiResult<String> updateResident(UUID residentId ,ResidentUpdateRequest request);

    ApiResult<String> deleteResident(UUID residentId);
}

package com.hungthinh.hungthinh.services.implement;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.mapper.ResidentMapper;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentGetResponse;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentUpdateRequest;
import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import com.hungthinh.hungthinh.models.entities.bases.Resident;
import com.hungthinh.hungthinh.models.entities.enums.RelationshipType;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.repositories.ApartmentRepository;
import com.hungthinh.hungthinh.repositories.ResidentRepository;
import com.hungthinh.hungthinh.services.interfaces.IResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResidentService implements IResidentService {
    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;
    private final ApartmentRepository apartmentRepository;
    @Override
    public ApiResult<List<ResidentGetResponse>> getAllResident() {
        List<ResidentGetResponse> residents = residentRepository.findAll()
                .stream()
                .map(residentMapper::toResponse)
                .toList();
        return ApiResult.success(residents, "Lấy danh sách resident thành công");
    }

    @Override
    public ApiResult<List<ResidentGetResponse>> getResidentByApartmentId(UUID apartmentId) {
        List<Resident> residents = residentRepository.findByApartmentId(apartmentId);

        List<ResidentGetResponse> responses = residents.stream()
                .map(residentMapper::toResponse)
                .sorted(Comparator.comparing((ResidentGetResponse r) -> !RelationshipType.OWNER.getDisplayName().equals(r.getRelation()))
                        .thenComparing(ResidentGetResponse::getLastModifiedTime, Comparator.nullsLast(Comparator.reverseOrder()))) // thời gian mới nhất đầu tiên
                .toList();
        return ApiResult.success(responses, "Lấy danh sách resident theo id căn hộ thành công");
    }

    @Override
    public ApiResult<UUID> createResident(UUID apartmentId, ResidentCreateRequest request) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new UserMessageException("Căn hộ không tồn tại"));

        Resident newResident = residentMapper.toEntity(request);
        newResident.setApartment(apartment);
        residentRepository.save(newResident);
        return ApiResult.success(newResident.getId(), "Thêm mới resident thành công");
    }

    @Override
    public ApiResult<String> updateResident(UUID residentId, ResidentUpdateRequest request) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new UserMessageException("Cư dân không tồn tại"));
        residentMapper.updateResident(request, resident);
        residentRepository.save(resident);
        return ApiResult.success(null, "Cập nhật resident thành công");
    }

    @Override
    public ApiResult<String> deleteResident(UUID residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new UserMessageException("Cư dân không tồn tại"));
        residentRepository.delete(resident);
        return ApiResult.success(null, "Xóa cư dân thành công");
    }
}

package com.hungthinh.hungthinh.services.implement;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.mapper.ApartmentMapper;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentGetResponse;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentUpdateRequest;
import com.hungthinh.hungthinh.models.dtos.user.UserCreateRequest;
import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import com.hungthinh.hungthinh.models.entities.bases.User;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.repositories.ApartmentRepository;
import com.hungthinh.hungthinh.repositories.UserRepository;
import com.hungthinh.hungthinh.services.interfaces.IApartmentService;
import com.hungthinh.hungthinh.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApartmentService implements IApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentmapper;
    private final IUserService userService;
    private final UserRepository userRepository;

    @Override
    public ApiResult<List<ApartmentGetResponse>> getApartments() {
        List<Apartment> apartmentList = apartmentRepository.findAll();
        List<ApartmentGetResponse> responseList = apartmentList.stream()
                .sorted(Comparator.comparing(Apartment::getLastModifiedTime).reversed())
                .map(apartmentmapper::toResponse)
                .toList();

        return ApiResult.success(responseList, "Lấy danh sách apartment thành công");
    }

    @Override
    @Transactional
    public ApiResult<UUID> createApartments(ApartmentCreateRequest request) {
        // tạo tài khoản RESIDENT
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role("RESIDENT")
                .build();

        ApiResult<UUID> userResult = userService.createUser(userCreateRequest);

        // Lấy user vừa tạo
        User owner = userRepository.findById(userResult.getData())
                .orElseThrow(() -> new UserMessageException("User Not Found"));

        Apartment newApartment = apartmentmapper.toEntity(request);

        newApartment.setOwner(owner);
        apartmentRepository.save(newApartment);

        return ApiResult.success(newApartment.getId(), "Thêm mới apaerment thành công");
    }

    @Override
    @Transactional
    public ApiResult<String> updateApartments(UUID apartmentId, ApartmentUpdateRequest request) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new UserMessageException("Căn hộ không tồn tại"));

        // mapper cập nhật entity
        apartmentmapper.updateEntityFromRequest(request, apartment);
        apartmentRepository.save(apartment);
        return ApiResult.success(null, "Cập nhật căn hộ thanh công");
    }

    @Override
    public ApiResult<String> deleteApartment(UUID apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new UserMessageException("Căn hộ không tồn tại"));
        apartmentRepository.delete(apartment);
        return ApiResult.success(null, "Xóa căn hộ thành công");
    }
}

package com.hungthinh.hungthinh.mapper;

import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentGetResponse;
import com.hungthinh.hungthinh.models.dtos.apartment.ApartmentUpdateRequest;
import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ApartmentMapper {

    // GET - Entity -> Response
    ApartmentGetResponse toResponse(Apartment apartment);

    // GET LIST - List<Entity> -> List<Response>
    List<ApartmentGetResponse> toResponseList(List<Apartment> apartments);

    // CREATE - Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Apartment toEntity(ApartmentCreateRequest request);

    // UPDATE - Request -> Existing Entity
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE,
            unmappedTargetPolicy = ReportingPolicy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateEntityFromRequest(
            ApartmentUpdateRequest request,
            @MappingTarget Apartment apartment
    );
}
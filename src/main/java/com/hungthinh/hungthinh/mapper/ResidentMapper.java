package com.hungthinh.hungthinh.mapper;

import com.hungthinh.hungthinh.models.dtos.resident.ResidentCreateRequest;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentGetResponse;
import com.hungthinh.hungthinh.models.dtos.resident.ResidentUpdateRequest;
import com.hungthinh.hungthinh.models.entities.bases.Resident;
import com.hungthinh.hungthinh.models.entities.enums.RelationshipType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ResidentMapper {

    // 1. Entity -> Response DTO
    @Mapping(target = "relation",
            expression = "java(resident.getRelationship() != null ? resident.getRelationship().getDisplayName() : null)")
    @Mapping(target = "apartmentId", source = "apartment.id")
    @Mapping(target = "apartmentNumber", source = "apartment.apartmentNumber")
    ResidentGetResponse toResponse(Resident resident);

    // 2. List<Entity> -> List<Response DTO>
    List<ResidentGetResponse> toResponseList(List<Resident> residents);

    // 3. Create DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "isTemporary", ignore = true)
    @Mapping(target = "relationship", source = "relation", qualifiedByName = "toRelationshipType")
    Resident toEntity(ResidentCreateRequest request);

    // 4. Update DTO -> Entity hiện tại
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            unmappedTargetPolicy = ReportingPolicy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    @Mapping(target = "relationship", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "isTemporary", ignore = true)
    void updateResident(ResidentUpdateRequest request, @MappingTarget Resident resident);

    // 5. String -> Enum
    @Named("toRelationshipType")
    default RelationshipType toRelationshipType(String relation) {
        if (relation == null || relation.trim().isEmpty()) {
            return null;
        }
        for (RelationshipType type : RelationshipType.values()) {
            if (type.name().equalsIgnoreCase(relation.trim()) || type.getDisplayName().equalsIgnoreCase(relation.trim())) {
                return type;
            }
        }
        return null;
    }
}

package com.hungthinh.hungthinh.repositories;


import com.hungthinh.hungthinh.models.entities.bases.Resident;
import com.hungthinh.hungthinh.models.entities.enums.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResidentRepository extends JpaRepository<Resident, UUID> {
    List<Resident> findByApartmentId(UUID apartmentId);

    List<Resident> findByRelationship(RelationshipType relationshipType);

    @Query("SELECT r FROM Resident r " +
            "JOIN FETCH r.apartment a " +
            "WHERE a.owner.id = :ownerId " +
            "AND r.relationship = :relationship")
    Optional<Resident> findOwnerInfoByUserId(@Param("ownerId") UUID ownerId,
                                             @Param("relationship") RelationshipType relationshipType);
}

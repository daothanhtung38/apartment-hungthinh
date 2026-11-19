package com.hungthinh.hungthinh.repositories;

import com.hungthinh.hungthinh.models.entities.bases.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {
    Optional<Apartment> findByApartmentNumber(String apartmentNumber);

    boolean existsByApartmentNumber(String apartmentNumber);
    Optional<Apartment> findByOwnerId(UUID ownerId);
}

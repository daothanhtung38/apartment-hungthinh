package com.hungthinh.hungthinh.models.entities.bases;

import com.hungthinh.hungthinh.models.entities.enums.ApartmentStarus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "apartments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Apartment extends BaseEntity {
    @Column(name = "apartment_number", nullable = false, length = 20, unique = true)
    private String apartmentNumber;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "block", length = 10)
    private String block;

    @Column(name = "area", precision = 8, scale = 2)
    private BigDecimal area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private ApartmentStarus status = ApartmentStarus.OCCUPIED;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<Resident> residents;

}

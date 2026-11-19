package com.hungthinh.hungthinh.models.entities.bases;

import com.hungthinh.hungthinh.models.entities.enums.RelationshipType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "residents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Resident extends BaseEntity {
    @Column(name = "fullname", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship", nullable = false)
    private RelationshipType relationship; // Chủ hộ, Vợ/Chồng, Con, Bố/Mẹ, Người thuê...

    @Builder.Default
    @Column(name = "is_temporary", nullable = false)
    private Boolean isTemporary = false;
}

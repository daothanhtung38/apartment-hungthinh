package com.hungthinh.hungthinh.models.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetsResponse {
    private UUID id;
    private String username;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime lastModifiedTime;
}

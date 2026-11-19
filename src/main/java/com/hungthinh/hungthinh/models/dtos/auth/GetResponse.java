package com.hungthinh.hungthinh.models.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetResponse {
    private String fullName;
    private String appamentNumber;
    private String relationShip;
}

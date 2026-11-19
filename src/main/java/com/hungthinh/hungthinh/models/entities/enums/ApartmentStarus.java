package com.hungthinh.hungthinh.models.entities.enums;


public enum ApartmentStarus {
    OCCUPIED("Đang ở"),
    VACANT("Trống"),
    UNDER_MAINTENANCE("Bảo trì");

    private final String displayName;

    ApartmentStarus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

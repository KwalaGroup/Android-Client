package com.kwala.app.enums;

/**
 * @author jacobamuchow@gmail.com
 */

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    UNKNOWN("UNKNOWN");

    private String networkValue;

    Gender(String networkValue) {
        this.networkValue = networkValue;
    }

    public static Gender fromNetworkValue(String networkValue) {
        for (Gender gender : values()) {
            if (gender.networkValue.equals(networkValue)) {
                return gender;
            }
        }

        return UNKNOWN;
    }

    public String getNetworkValue() {
        return networkValue;
    }
}

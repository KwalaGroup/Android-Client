package com.kwala.app.enums;

/**
 * @author jacobamuchow@gmail.com
 */

public enum Interest {
    UNKNOWN("UNKNOWN", "Unknown"),
    MARRIAGE("MARRIAGE", "Marriage"),
    DATING("DATING", "Dating"),
    CASUAL("CASUAL", "Casual"),
    TEA_NOT_COFFEE("TEA_NOT_COFFEE", "Tea Not Coffee"),
    POUR_OVER("POUR_OVER", "Pour Over"),
    LATTES("LATTES", "Lattes"),
    JUST_COFFEE("JUST_COFFEE", "Just Coffee"),
    YOGA("YOGA","Yoga"),
    BODYBUILDING("BODYBUILDING", "Building"),
    POWER_LIFTING("POWER_LIFTING", "Power Lifting"),
    RUNNING("RUNNING", "Running"),
    BIKING("BIKING", "Biking"),
    TRIATHLONS("TRIATHLONS", "Triathlons"),
    CHEMISTRY("CHEMISTRY", "Chemistry"),
    PHYSICS("PHYSICS", "Physics"),
    MATH("MATH", "Math"),
    OPERATING_SYSTEMS("OPERATING_SYSTEMS", "Operating Systems"),
    COOKING("COOKING", "Cooking"),
    HIKING("HIKING", "Hiking"),
    READING("READING", "Reading"),
    DRINKING("DRINKING", "Drinking"),
    ACADEMIC_INTEGRITY("ACADEMIC_INTEGRITY", "Academic Integrity"),
    MUTUALLY_EXCLUSIVE("MUTUALLY_EXCLUSIVE", "Mutually Exclusive");

    private String networkString;
    private String displayString;

    Interest(String networkString, String displayString) {
        this.networkString = networkString;
        this.displayString = displayString;
    }

    public static Interest fromNetworkString(String networkString) {
        for (Interest interest : values()) {
            if (interest.getNetworkString().equals(networkString)) {
                return interest;
            }
        }
        return UNKNOWN;
    }

    public String getNetworkString() {
        return networkString;
    }

    public String getDisplayString() {
        return displayString;
    }
}

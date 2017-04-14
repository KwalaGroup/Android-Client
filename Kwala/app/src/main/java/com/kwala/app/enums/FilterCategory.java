package com.kwala.app.enums;

import android.support.annotation.DrawableRes;

import com.kwala.app.R;

/**
 * @author muchow@hello.com
 */
public enum FilterCategory {
    UNKNOWN("UNKNOWN", "Unknown", 0, new String[]{}),
    LOVE_INTEREST("LOVE_INTEREST", "Love Interest", R.drawable.filter_heart_icon, new String[] { "Marriage", "Dating", "Casual" , "Mutually Exclusive"}),
    COFFEE_BUDDY("COFFEE_BUDDY", "Coffee Buddy", R.drawable.filter_coffee_icon, new String[] {"Tea Not Coffee", "Pour Over", "Lattes", "Just Coffee"}),
    WORKOUT_BUDDY("WORKOUT_BUDDY", "Workout Buddy", R.drawable.filter_weights_icon, new String[] {"Yoga", "Bodybuilding", "Power lifting", "Running", "Biking", "Triathlons"}),
    STUDY_BUDDY("STUDY_BUDDY", "Study Buddy", R.drawable.filter_pencil_icon, new String[] {"Chemistry", "Physics", "Math", "Operating Systems", "Academic Integrity"}),
    BUDDY("BUDDY", "Buddy", R.drawable.filter_arrows_icon, new String[] {"Cooking", "Hiking", "Reading", "Drinking"});

    public enum Interests {
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

        Interests(String networkString, String displayString) {
            this.networkString = networkString;
            this.displayString = displayString;
        }

        public static Interests fromNetworkString(String networkString) {
            for (Interests interest : values()) {
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

    public static final FilterCategory[] supportedCategories = {
        LOVE_INTEREST, COFFEE_BUDDY, WORKOUT_BUDDY, STUDY_BUDDY, BUDDY
    };

    private String networkString;
    private String displayString;
    @DrawableRes private int iconId;
    private String[] interests;

    FilterCategory(String networkString, String displayString, @DrawableRes int iconId, String[] interests) {
        this.networkString = networkString;
        this.displayString = displayString;
        this.iconId = iconId;
        this.interests = interests;
    }

    public static FilterCategory fromNetworkString(String networkString) {
        for (FilterCategory category : values()) {
            if (category.getNetworkString().equals(networkString)) {
                return category;
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

    @DrawableRes
    public int getIconId() {
        return iconId;
    }

    public String[] getInterests() {
        return interests;
    }
}

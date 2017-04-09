package com.kwala.app.enums;

import android.support.annotation.DrawableRes;

import com.kwala.app.R;

/**
 * @author muchow@hello.com
 */
public enum FilterCategory {
    UNKNOWN("UNKNOWN", "Unknown", 0, new String[]{}),
    LOVE_INTEREST("LOVE_INTEREST", "Love Interest", R.drawable.filter_heart_icon, new String[] { "Marriage", "Dating", "Casual"}),
    COFFEE_BUDDY("COFFEE_BUDDY", "Coffee Buddy", R.drawable.filter_coffee_icon, new String[] {"Tea Not Coffee", "Pour Over", "White Girl Lattes", "Just Coffee"}),
    WORKOUT_BUDDY("WORKOUT_BUDDY", "Workout Buddy", R.drawable.filter_weights_icon, new String[] {"Yoga", "Bodybuilding", "Power lifting", "Running", "Biking", "Triathlons"}),
    STUDY_BUDDY("STUDY_BUDDY", "Study Buddy", R.drawable.filter_pencil_icon, new String[] {"Chemistry", "Physics", "Math", "OS"}),
    BUDDY("BUDDY", "Buddy", R.drawable.filter_arrows_icon, new String[] {"Cooking", "Hiking", "Reading", "Drinking"});

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

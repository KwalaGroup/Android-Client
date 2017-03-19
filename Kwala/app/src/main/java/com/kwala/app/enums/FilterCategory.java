package com.kwala.app.enums;

import android.support.annotation.DrawableRes;

import com.kwala.app.R;

/**
 * @author muchow@hello.com
 */
public enum FilterCategory {
    UNKNOWN("UNKNOWN", "Unknown", 0),
    LOVE_INTEREST("LOVE_INTEREST", "Love Interest", R.drawable.filter_heart_icon),
    COFFEE_BUDDY("COFFEE_BUDDY", "Coffee Buddy", R.drawable.filter_coffee_icon),
    WORKOUT_BUDDY("WORKOUT_BUDDY", "Workout Buddy", R.drawable.filter_weights_icon),
    ONE_NIGHT_STAND("ONE_NIGHT_STAND", "One Night Stand ;)", R.drawable.filter_lips_icon),
    STUDY_BUDDY("STUDY_BUDDY", "Study Buddy", R.drawable.filter_pencil_icon),
    BUDDY("BUDDY", "Buddy", R.drawable.filter_arrows_icon);
    //TODO: add icons

    public static final FilterCategory[] supportedCategories = {
        LOVE_INTEREST, COFFEE_BUDDY, WORKOUT_BUDDY, ONE_NIGHT_STAND, STUDY_BUDDY, BUDDY
    };

    private String networkString;
    private String displayString;
    @DrawableRes private int iconId;

    FilterCategory(String networkString, String displayString, @DrawableRes int iconId) {
        this.networkString = networkString;
        this.displayString = displayString;
        this.iconId = iconId;
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
}

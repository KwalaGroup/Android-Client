package com.kwala.app.enums;

import android.support.annotation.DrawableRes;

import com.kwala.app.R;

/**
 * @author muchow@hello.com
 */
public enum FilterCategory {
    UNKNOWN("UNKNOWN", "Unknown", R.drawable.brain_icon),
    LOVE_INTEREST("LOVE_INTEREST", "Love Interest", R.drawable.brain_icon),
    COFFEE_BUDDY("COFFEE_BUDDY", "Coffee Buddy", R.drawable.brain_icon),
    WORKOUT_BUDDY("WORKOUT_BUDDY", "Workout Buddy", R.drawable.brain_icon),
    ONE_NIGHT_STAND("ONE_NIGHT_STAND", "One Night Stand ;)", R.drawable.brain_icon);
    //TODO: add icons

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

package com.kwala.app.enums;

import android.support.annotation.DrawableRes;

import com.kwala.app.R;

/**
 * @author muchow@hello.com
 */
public enum Filter {
    UNKNOWN("UNKNOWN", "Unknown", 0, new Interest[]{}),
    LOVE_INTEREST("LOVE_INTEREST", "Love Interest", R.drawable.filter_heart_icon, new Interest[] { Interest.MARRIAGE, Interest.DATING, Interest.CASUAL, Interest.MUTUALLY_EXCLUSIVE }),
    COFFEE_BUDDY("COFFEE_BUDDY", "Coffee Buddy", R.drawable.filter_coffee_icon, new Interest[] { Interest.TEA_NOT_COFFEE, Interest.POUR_OVER, Interest.LATTES, Interest.JUST_COFFEE }),
    WORKOUT_BUDDY("WORKOUT_BUDDY", "Workout Buddy", R.drawable.filter_weights_icon, new Interest[] {Interest.YOGA, Interest.BODYBUILDING, Interest.POWER_LIFTING, Interest.RUNNING, Interest.BIKING, Interest.TRIATHLONS }),
    STUDY_BUDDY("STUDY_BUDDY", "Study Buddy", R.drawable.filter_pencil_icon, new Interest[] { Interest.CHEMISTRY, Interest.PHYSICS, Interest.MATH, Interest.OPERATING_SYSTEMS, Interest.ACADEMIC_INTEGRITY }),
    BUDDY("BUDDY", "Buddy", R.drawable.filter_arrows_icon, new Interest[] {Interest.COOKING, Interest.HIKING, Interest.READING, Interest.DRINKING });

    public static final Filter[] supportedCategories = {
        LOVE_INTEREST, COFFEE_BUDDY, WORKOUT_BUDDY, STUDY_BUDDY, BUDDY
    };

    private String networkString;
    private String displayString;
    @DrawableRes private int iconId;
    private Interest[] interests;

    Filter(String networkString, String displayString, @DrawableRes int iconId, Interest[] interests) {
        this.networkString = networkString;
        this.displayString = displayString;
        this.iconId = iconId;
        this.interests = interests;
    }

    public static Filter fromNetworkString(String networkString) {
        for (Filter category : values()) {
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

    public Interest[] getInterests() {
        return interests;
    }
}

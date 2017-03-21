package com.kwala.app.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class MainTabLayout extends TabLayout {
    private static final String TAG = MainTabLayout.class.getSimpleName();
    
    public MainTabLayout(Context context) {
        super(context);
    }

    public MainTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        
        getTabAt(0).setIcon(R.drawable.brain_icon);
        getTabAt(1).setIcon(R.drawable.filters_icon);
        getTabAt(2).setIcon(R.drawable.matches_icon);
        getTabAt(3).setIcon(R.drawable.profile_icon);
        getTabAt(4).setIcon(R.drawable.settings_icon);

        addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                updateTabColor(tab, true);
            }

            @Override
            public void onTabUnselected(Tab tab) {
                updateTabColor(tab, false);
            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });

        updateTabColor(getTabAt(0), true);
    }

    private void updateTabColor(Tab tab, boolean selected) {
        if (selected) {
            int color = Color.WHITE;

            switch (tab.getPosition()) {
                case 0: color = ContextCompat.getColor(getContext(), R.color.kPink); break;
                case 1: color = ContextCompat.getColor(getContext(), R.color.kYellow); break;
                case 2: color = ContextCompat.getColor(getContext(), R.color.kLightBlue); break;
                case 3: color = ContextCompat.getColor(getContext(), R.color.kOrange); break;
                case 4: color = ContextCompat.getColor(getContext(), R.color.kWhite); break;
            }

            tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            setSelectedTabIndicatorColor(color);
        } else {
            tab.getIcon().setColorFilter(null);
        }
    }
}

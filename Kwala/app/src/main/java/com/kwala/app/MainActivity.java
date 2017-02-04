package com.kwala.app;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kwala.app.matches.MatchesFragment;
import com.kwala.app.service.tasks.ITunesRssTask;
import com.kwala.app.service.tasks.Task;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
        References
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tabLayout = (TabLayout) findViewById(R.id.main_activity_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);

        pagerAdapter = createAdapter();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.brain_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.filters_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.matches_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.profile_icon);
        tabLayout.getTabAt(4).setIcon(R.drawable.settings_icon);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabColor(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabColor(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        updateTabColor(tabLayout.getTabAt(0), true);

        new ITunesRssTask().start(new Task.Callback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d(TAG, "UI success: " + jsonObject.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "UI failure", e);
            }
        });
    }

    private void updateTabColor(TabLayout.Tab tab, boolean selected) {
        if (selected) {
            int color = Color.WHITE;

            switch (tab.getPosition()) {
                case 0: color = Color.RED; break;
                case 1: color = Color.YELLOW; break;
                case 2: color = Color.BLUE; break;
                case 3: color = Color.MAGENTA; break;
                case 4: color = Color.WHITE; break;
            }

            tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            tabLayout.setSelectedTabIndicatorColor(color);
        } else {
            tab.getIcon().setColorFilter(null);
        }
    }

    private PagerAdapter createAdapter() {
        return new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MatchesFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }
}

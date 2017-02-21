package com.kwala.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kwala.app.filters.FiltersFragment;
import com.kwala.app.matches.MatchesFragment;
import com.kwala.app.profile.MyProfileFragment;
import com.kwala.app.quiz.QuizFragment;
import com.kwala.app.settings.SettingsFragment;

/**
 * @author jacobamuchow@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
        References
     */
    private MainTabLayout tabLayout;
    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tabLayout = (MainTabLayout) findViewById(R.id.main_activity_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);

        pagerAdapter = createAdapter();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private PagerAdapter createAdapter() {
        return new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return QuizFragment.newInstance();
                    case 1: return FiltersFragment.newInstance();
                    case 2: return MatchesFragment.newInstance();
                    case 3: return MyProfileFragment.newInstance();
                    case 4: return SettingsFragment.newInstance();
                    default: return null;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }
}

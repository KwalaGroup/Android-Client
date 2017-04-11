package com.kwala.app.main;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kwala.app.R;
import com.kwala.app.filters.FiltersFragment;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.matches.MatchesFragment;
import com.kwala.app.profile.MyProfileFragment;
import com.kwala.app.quiz.QuizFragment;
import com.kwala.app.service.LocationService;
import com.kwala.app.settings.SettingsFragment;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author jacobamuchow@gmail.com
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
        References
     */
    private MainTabLayout tabLayout;
    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    /*
        Constructors
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tabLayout = (MainTabLayout) findViewById(R.id.main_activity_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);

        pagerAdapter = createAdapter();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(2);

        Intent intent = new Intent(this, LocationService.class);
        startService(intent);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = database.getReference();

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "data: " + dataSnapshot.toString());

                JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                Log.d(TAG, "json: " + jsonObject);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Firebase database error", databaseError.toException());
            }
        });
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

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 2) {
            viewPager.setCurrentItem(2);
        } else {
            super.onBackPressed();
        }
    }
}

package com.kwala.app;

import android.*;
import android.Manifest;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kwala.app.filters.FiltersFragment;
import com.kwala.app.helpers.BaseActivity;
import com.kwala.app.matches.MatchesFragment;
import com.kwala.app.profile.MyProfileFragment;
import com.kwala.app.quiz.QuizFragment;
import com.kwala.app.settings.SettingsFragment;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * @author jacobamuchow@gmail.com
 */
public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    GoogleApiClient mGoogleApiClient;

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

        viewPager.setCurrentItem(2);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Nammu.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                if (mGoogleApiClient.isConnected()) {
                    Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        Log.d(TAG, "" + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());
                    }
                }
            }

            @Override
            public void permissionRefused() {
                // :(
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "connection failed: " + connectionResult.toString());
    }
}

package com.kwala.app.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseActivity;
import com.kwala.app.login.LandingActivity;
import com.kwala.app.service.UserData;

/**
 * @author jacobamuchow@gmail.com
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        if (UserData.getInstance().isLoggedIn()) {
            startActivity(MainActivity.newIntent(this));
        } else {
            startActivity(LandingActivity.newIntent(this));
        }

        finishAffinity();
    }
}

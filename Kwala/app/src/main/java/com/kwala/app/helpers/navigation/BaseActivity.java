package com.kwala.app.helpers.navigation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import pl.tajchert.nammu.Nammu;

/**
 * @author jacobamuchow@gmail.com
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

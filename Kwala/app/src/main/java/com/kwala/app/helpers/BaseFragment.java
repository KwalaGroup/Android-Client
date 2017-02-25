package com.kwala.app.helpers;

import android.app.Fragment;
import android.support.annotation.NonNull;

import pl.tajchert.nammu.Nammu;

/**
 * @author jacobamuchow@gmail.com
 */

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

package com.kwala.app.helpers.navigation;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.kwala.app.helpers.PhotoHelper;

import pl.tajchert.nammu.Nammu;

/**
 * @author jacobamuchow@gmail.com
 */

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof BaseActivity)) {
            throw new IllegalStateException("BaseFragments must belong to BaseActivities");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PhotoHelper.handleOnActivityResult(requestCode, resultCode, data, getBaseActivity())) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * May be unsafe cast. This is enforced by the IllegalStateException thrown in onAttach()
     * if the parent Activity is not an instance of BaseActivity.
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}

package com.kwala.app.filters;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */
public class FiltersFragment extends Fragment {
    private static final String TAG = FiltersFragment.class.getSimpleName();

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filters_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

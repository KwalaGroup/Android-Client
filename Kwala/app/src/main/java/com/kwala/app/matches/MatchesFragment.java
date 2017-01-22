package com.kwala.app.matches;

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
public class MatchesFragment extends Fragment {
    private static final String TAG = MatchesFragment.class.getSimpleName();

    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.matches_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

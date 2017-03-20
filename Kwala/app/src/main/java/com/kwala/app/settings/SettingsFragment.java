package com.kwala.app.settings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kwala.app.R;
import com.kwala.app.login.LandingActivity;
import com.kwala.app.service.DataStore;
import com.kwala.app.service.tasks.auth.LogoutTask;

/**
 * @author jacobamuchow@gmail.com
 */

public class SettingsFragment extends Fragment {
    private static final String TAG = SettingsFragment.class.getSimpleName();

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signOutButton = (Button) view.findViewById(R.id.settings_logout_button);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataStore.getInstance().clearAllData();

                new LogoutTask().start(null);

                Intent intent = LandingActivity.newIntent(getActivity());
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });
    }
}

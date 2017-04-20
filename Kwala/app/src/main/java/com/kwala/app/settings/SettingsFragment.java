package com.kwala.app.settings;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kwala.app.BuildConfig;
import com.kwala.app.R;

import java.util.Locale;

import de.jonasrottmann.realmbrowser.RealmBrowser;
import io.realm.Realm;

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

        TextView versionTextView = (TextView) view.findViewById(R.id.settings_version_text);
        Button signOutButton = (Button) view.findViewById(R.id.settings_logout_button);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNext-Medium.ttf");
        versionTextView.setTypeface(typeface);

        versionTextView.setText(String.format(Locale.US, "%s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new LogoutTask().start(null);
//
//                //Stop location update service
//                Intent intent = new Intent(getActivity(), LocationService.class);
//                getActivity().stopService(intent);
//
//                DataStore.getInstance().clearAllData();
//
//                intent = LandingActivity.newIntent(getActivity());
//                startActivity(intent);
//                getActivity().finishAffinity();

                RealmBrowser.startRealmModelsActivity(getActivity(), Realm.getDefaultInstance().getConfiguration());
            }
        });
    }
}

package com.kwala.app.settings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kwala.app.BuildConfig;
import com.kwala.app.R;
import com.kwala.app.login.LandingActivity;
import com.kwala.app.service.DataStore;
import com.kwala.app.service.LocationService;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.auth.LogoutTask;

import java.util.Locale;

import de.jonasrottmann.realmbrowser.RealmBrowser;
import io.realm.Realm;

import static android.view.View.GONE;

/**
 * @author jacobamuchow@gmail.com
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {
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

        View debugOptionsLayout = view.findViewById(R.id.settings_debug_options_layout);
        View realmBrowserCell = view.findViewById(R.id.settings_realm_browser_cell);
        View clearRealmCell = view.findViewById(R.id.settings_clear_realm_cell);
        View clearCacheCell = view.findViewById(R.id.settings_clear_cache_cell);

        TextView versionTextView = (TextView) view.findViewById(R.id.settings_version_text);
        Button signOutButton = (Button) view.findViewById(R.id.settings_logout_button);

        versionTextView.setText(String.format(Locale.US, "%s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));

        if (BuildConfig.DEBUG) {
            debugOptionsLayout.setVisibility(View.VISIBLE);

            realmBrowserCell.setOnClickListener(this);
            clearRealmCell.setOnClickListener(this);
            clearCacheCell.setOnClickListener(this);
        } else {
            debugOptionsLayout.setVisibility(GONE);
        }

        signOutButton.setOnClickListener(this);
    }

    private void logout() {
        new LogoutTask().start(null);

        //Stop location update service
        Intent intent = new Intent(getActivity(), LocationService.class);
        getActivity().stopService(intent);

        DataStore.getInstance().clearAllData();

        intent = LandingActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().finishAffinity();
    }

    /**
     * Listeners
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_realm_browser_cell:
                RealmBrowser.startRealmModelsActivity(getActivity(), Realm.getDefaultInstance().getConfiguration());
                break;

            case R.id.settings_clear_realm_cell:
                Log.d(TAG, "clearing realm... nom nom nom");
                RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
                    @Override
                    public Void execute(Realm realm) {
                        realm.deleteAll();
                        return null;
                    }
                });
                break;

            case R.id.settings_clear_cache_cell:
                DataStore.getInstance().getNetworkStore().clearData();
                break;

            case R.id.settings_logout_button:
                logout();
                break;
        }
    }
}

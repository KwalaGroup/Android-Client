package com.kwala.app.main;

import android.app.Application;
import android.util.Log;

import com.kwala.app.service.DataStore;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmError;
import pl.tajchert.nammu.Nammu;

/**
 * @author jacobamuchow@gmail.com
 */
public class KwalaApplication extends Application {
    private static final String TAG = KwalaApplication.class.getSimpleName();

    private static KwalaApplication application;

    public static KwalaApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        Nammu.init(this);

        /*
         * Configure Realm
         */
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        try {
            DataStore.getInstance();
        } catch (RealmError e) {
            Log.e(TAG, "Error configuring realm, deleting and trying again.", e);

            Realm.deleteRealm(realmConfiguration);
            DataStore.getInstance();
        }
    }
}

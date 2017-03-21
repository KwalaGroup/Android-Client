package com.kwala.app.service;

import com.kwala.app.service.realm.RealmWrites;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */
public class DataStore {
    private static final String TAG = DataStore.class.getSimpleName();

    private static DataStore dataStore;

    private NetworkStore networkStore;

    private Realm realm;

    /*
        Constructors
     */
    private DataStore() {
        networkStore = new NetworkStore();
        realm = Realm.getDefaultInstance();
    }

    public static synchronized DataStore getInstance() {
        if (dataStore == null) {
            dataStore = new DataStore();
        }
        return dataStore;
    }

    public void clearAllData() {
        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                realm.deleteAll();
                return null;
            }
        });

        UserData.getInstance().clearData();
        networkStore.clearData();
    }

    public NetworkStore getNetworkStore() {
        return networkStore;
    }

    public Realm getRealm() {
        return realm;
    }
}

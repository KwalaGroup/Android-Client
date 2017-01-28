package com.kwala.app.service;

/**
 * @author jacobamuchow@gmail.com
 */
public class DataStore {
    private static final String TAG = DataStore.class.getSimpleName();

    private static DataStore dataStore;

    private NetworkStore networkStore;

    /*
        Constructors
     */
    private DataStore() {
        networkStore = new NetworkStore();
    }

    public static synchronized DataStore getInstance() {
        if (dataStore == null) {
            dataStore = new DataStore();
        }
        return dataStore;
    }

    public NetworkStore getNetworkStore() {
        return networkStore;
    }
}

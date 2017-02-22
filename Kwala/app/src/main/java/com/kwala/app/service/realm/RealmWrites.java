package com.kwala.app.service.realm;

import android.support.annotation.Nullable;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * @author jacobamuchow@gmail.com
 */

public class RealmWrites {
    private static final String TAG = RealmWrites.class.getSimpleName();

    private Realm realm;
    private boolean isDefaultRealm;

    /*
        Constructors
     */
    private RealmWrites(Realm realm, boolean isDefaultRealm) {
        this.realm = realm;
        this.isDefaultRealm = isDefaultRealm;
    }

    public static RealmWrites withRealm(Realm realm) {
        return new RealmWrites(realm, false);
    }

    public static RealmWrites withDefaultRealm() {
        return new RealmWrites(Realm.getDefaultInstance(), true);
    }

    public <T extends RealmModel> T findOrCreate(Class<T> clazz, String primaryKey) {
        T object = RealmQueries.withRealm(realm).get(clazz, primaryKey);
        if (object == null) {
            object = realm.createObject(clazz, primaryKey);
        }
        return object;
    }

    @Nullable
    public <T> T executeTransaction(final Transaction<T> transaction) {

        T result = null;

        /*
         *
         */
        realm.beginTransaction();
        try {
            result = transaction.execute(realm);
            realm.commitTransaction();
        } catch (Throwable e) {
            if (realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            Log.e(TAG, "Error performing Realm transaction", e);
        }

        if (isDefaultRealm) {
            realm.close();
        }

        return result;
    }

    public interface Transaction<T> {
        T execute(Realm realm);
    }
}

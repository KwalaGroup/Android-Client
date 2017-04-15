package com.kwala.app.service.realm;

import android.support.annotation.Nullable;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;

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

    /**
     * Looks for an object in Realm for the model class and primary key given. If it does not exist,
     * a row is created in Realm for that model and returned.
     * @return The model found or created.
     */
    public <T extends RealmModel> T findOrCreate(Class<T> clazz, String primaryKey) {
        T model = RealmQueries.withRealm(realm).get(clazz, primaryKey);
        if (model == null) {
            model = realm.createObject(clazz, primaryKey);
        }
        return model;
    }

    /**
     * Deletes an row from Realm for the model class and primary key given if it exists.
     * @return true if the object existed, false if not.
     */
    public <T extends RealmModel> boolean deleteIfExists(Class<T> clazz, String primaryKey) {
        T model = RealmQueries.withRealm(realm).get(clazz, primaryKey);
        if (model == null) {
            return false;
        }
        RealmObject.deleteFromRealm(model);
        return false;
    }

    /**
     * Boiler plate wrapper for Realm transactions. Handles beginning, committing and cancelling
     * transactions on error. Also handles closing of the Realm if a new reference was retrieved.
     *
     * @return An optional type T which may be the "result" of your transaction or whatever
     * else you wish to have access to after the code block completes.
     */
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

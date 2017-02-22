package com.kwala.app.service.realm;

import android.support.annotation.MainThread;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class RealmQueries {
    private static final String TAG = RealmQueries.class.getSimpleName();

    private Realm realm;

    /*
        Constructors
     */
    private RealmQueries(Realm realm) {
        this.realm = realm;
    }

    @MainThread
    public static RealmQueries withMainRealm() {
        return new RealmQueries(Realm.getDefaultInstance());
    }

    public static RealmQueries withRealm(Realm realm) {
        return new RealmQueries(realm);
    }

    /*
        Generics
     */
    public <T extends RealmModel> T get(Class<T> clazz, String primaryKey) {
        String primaryKeyFieldName = realm.getSchema().get(clazz.getSimpleName()).getPrimaryKey();
        return realm.where(clazz).equalTo(primaryKeyFieldName, primaryKey).findFirst();
    }

    public <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }


}

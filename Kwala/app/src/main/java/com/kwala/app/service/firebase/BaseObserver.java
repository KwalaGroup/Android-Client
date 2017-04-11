package com.kwala.app.service.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * @author jacobamuchow@gmail.com
 */

public abstract class BaseObserver<Model> extends GenericChildEventListener<Model> {
    private static final String TAG = BaseObserver.class.getSimpleName();

    protected Query query;

    private boolean listening = false;

    protected BaseObserver(Class<Model> clazz, String childPath) {
        super(clazz);
        this.query = FirebaseDatabase.getInstance().getReference(childPath);
    }

    public void startListening() {
        if (!listening) {
            query.addChildEventListener(this);
        }
    }

    public void stopListening() {
        if (listening) {
            query.removeEventListener(this);
        }
    }

    @Override
    protected void onError(DatabaseError error) {
        Log.e(TAG, "Firebase observer database error", error.toException());
    }
}

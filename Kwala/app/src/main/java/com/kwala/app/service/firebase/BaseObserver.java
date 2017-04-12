package com.kwala.app.service.firebase;

import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * @author jacobamuchow@gmail.com
 */

public abstract class BaseObserver<Model> implements ChildEventListener {
    private static final String TAG = BaseObserver.class.getSimpleName();

    private Class<Model> clazz;
    protected Query query;
    private boolean listening = false;
    @Nullable private Listener listener;

    public interface Listener {
        void onUpdate();
        void onError(DatabaseError error);
    }

    protected BaseObserver(Class<Model> clazz, String childPath) {
        this.clazz = clazz;
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

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }

    private void callListenerUpdate() {
        if (listener != null) {
            listener.onUpdate();
        }
    }


    protected abstract void onChildAdded(String key, Model model);

    protected abstract void onChildChanged(String key, Model model);

    protected abstract void onChildRemoved(String key);

    protected abstract void onChildMoved(String key, Model model);

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        onChildAdded(dataSnapshot.getKey(), dataSnapshot.getValue(clazz));
        callListenerUpdate();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        onChildChanged(dataSnapshot.getKey(), dataSnapshot.getValue(clazz));
        callListenerUpdate();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        onChildRemoved(dataSnapshot.getKey());
        callListenerUpdate();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        onChildMoved(dataSnapshot.getKey(), dataSnapshot.getValue(clazz));
        callListenerUpdate();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (listener != null) {
            listener.onError(databaseError);
        }
    }
}

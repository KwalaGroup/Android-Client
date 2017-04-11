package com.kwala.app.service.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * @author jacobamuchow@gmail.com
 */

public abstract class GenericChildEventListener<Model> implements ChildEventListener {
    private static final String TAG = GenericChildEventListener.class.getSimpleName();

    private Class<Model> clazz;

    protected GenericChildEventListener(Class<Model> clazz) {
        this.clazz = clazz;
    }

    protected abstract void onChildAdded(String key, Model model);

    protected abstract void onChildChanged(String key, Model model);

    protected abstract void onChildRemoved(String key);

    protected abstract void onChildMoved(String key, Model model);

    protected abstract void onError(DatabaseError error);

    @Override
    public final void onChildAdded(DataSnapshot dataSnapshot, String s) {
        onChildAdded(dataSnapshot.getKey(), dataSnapshot.getValue(clazz));
    }

    @Override
    public final void onChildChanged(DataSnapshot dataSnapshot, String s) {
        onChildChanged(dataSnapshot.getKey(), dataSnapshot.getValue(clazz));
    }

    @Override
    public final void onChildRemoved(DataSnapshot dataSnapshot) {
        onChildRemoved(dataSnapshot.getKey());
    }

    @Override
    public final void onChildMoved(DataSnapshot dataSnapshot, String s) {
        onChildMoved(dataSnapshot.getKey(), dataSnapshot.getValue(clazz));
    }

    @Override
    public final void onCancelled(DatabaseError databaseError) {
        onError(databaseError);
    }
}

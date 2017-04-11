package com.kwala.app.service.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.kwala.app.models.FBMessage;
import com.kwala.app.models.RMessage;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class ChatObserver extends GenericChildEventListener<FBMessage> {
    private static final String TAG = ChatObserver.class.getSimpleName();

    public ChatObserver(String matchId) {
        super(FBMessage.class);
        FirebaseDatabase.getInstance().getReference("match-chats").child(matchId).addChildEventListener(this);
    }

    @Override
    protected void onChildAdded(String key, FBMessage fbMessage) {
        writeMessage(key, fbMessage);
    }

    @Override
    protected void onChildChanged(String key, FBMessage fbMessage) {
        writeMessage(key, fbMessage);
    }

    @Override
    protected void onChildRemoved(String key) {
        deleteMessage(key);
    }

    @Override
    protected void onChildMoved(String key, FBMessage fbMessage) {
        writeMessage(key, fbMessage);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "Database error", databaseError.toException());
    }

    private void writeMessage(final String key, final FBMessage fbMessage) {

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {

                RealmSyncs.withRealm(realm).syncMessage(key, fbMessage);

                return null;
            }
        });
    }

    private void deleteMessage(final String key) {

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                RMessage message = RealmWrites.withRealm(realm).findOrCreate(RMessage.class, key);
                message.deleteFromRealm();
                return null;
            }
        });
    }
}

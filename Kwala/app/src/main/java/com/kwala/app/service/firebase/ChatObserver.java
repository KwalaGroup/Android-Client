package com.kwala.app.service.firebase;

import com.kwala.app.models.FBMessage;
import com.kwala.app.models.RMessage;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class ChatObserver extends BaseObserver<FBMessage> {
    private static final String TAG = ChatObserver.class.getSimpleName();

    private String matchId;

    public ChatObserver(String matchId) {
        super(FBMessage.class, "match-chats/" + matchId);
        this.matchId = matchId;
    }

    @Override
    protected void onChildAdded(String key, FBMessage fbMessage) {
        writeMessage(fbMessage);
    }

    @Override
    protected void onChildChanged(String key, FBMessage fbMessage) {
        writeMessage(fbMessage);
    }

    @Override
    protected void onChildRemoved(String key) {
        deleteMessage(key);
    }

    @Override
    protected void onChildMoved(String key, FBMessage fbMessage) {
        writeMessage(fbMessage);
    }

    private void writeMessage(final FBMessage fbMessage) {

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                RealmSyncs.withRealm(realm).syncMessage(matchId, fbMessage);
                return null;
            }
        });
    }

    private void deleteMessage(final String key) {

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                //Key in FB is message ID
                RMessage message = RealmWrites.withRealm(realm).findOrCreate(RMessage.class, key);
                message.deleteFromRealm();
                return null;
            }
        });
    }
}

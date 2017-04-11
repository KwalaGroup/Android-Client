package com.kwala.app.matches.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.models.RMessage;
import com.kwala.app.service.firebase.ChatObserver;
import com.kwala.app.service.realm.RealmQueries;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class ChatActivity extends BaseActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

    private RealmResults<RMessage> messages;

    public static Intent newIntent(Context context, String matchId) {
        Intent intent = new Intent(context, ChatActivity.class);

        intent.putExtra(MATCH_ID_KEY, matchId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        String matchId = getIntent().getStringExtra(MATCH_ID_KEY);
        Log.d(TAG, "matchId: " + matchId);

        ChatObserver chatObserver = new ChatObserver(matchId);
        chatObserver.startListening();

        messages = RealmQueries.withMainRealm().getMessages(matchId);
        logMessages();

        messages.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<RMessage>>() {
            @Override
            public void onChange(RealmResults<RMessage> collection, OrderedCollectionChangeSet changeSet) {
                messages = collection;
                logMessages();
            }
        });
    }

    private void logMessages() {
        Log.d(TAG, " ");
        for (RMessage message : messages) {
            message.log();
        }
        Log.d(TAG, " ");
    }
}

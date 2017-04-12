package com.kwala.app.matches.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KRealmRecyclerViewAdapter;
import com.kwala.app.models.RMessage;
import com.kwala.app.service.UserData;
import com.kwala.app.service.firebase.ChatObserver;
import com.kwala.app.service.realm.RealmQueries;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class ChatActivity extends BaseActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

    private RecyclerView recyclerView;
    private KRealmRecyclerViewAdapter<RMessage> adapter;

    private String matchId;
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

        /*
         * Get view references
         */
        recyclerView = (RecyclerView) findViewById(R.id.chat_activity_recycler_view);

        /*
         * Set view data
         */
        matchId = getIntent().getStringExtra(MATCH_ID_KEY);

        ChatObserver chatObserver = new ChatObserver(matchId);
        chatObserver.startListening();

        adapter = createAdapter();
        recyclerView.setAdapter(adapter);

//        logMessages();
    }

    private KRealmRecyclerViewAdapter<RMessage> createAdapter() {
        messages = RealmQueries.withMainRealm().getMessages(matchId);

        return new KRealmRecyclerViewAdapter<RMessage>(this, messages, true) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 2) {
                    RightChatCell rightChatCell = new RightChatCell(parent.getContext());
                    return new RecyclerView.ViewHolder(rightChatCell) {};
                } else {
                    LeftChatCell leftChatCell = new LeftChatCell(parent.getContext());
                    return new RecyclerView.ViewHolder(leftChatCell) {};
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                if (holder.itemView instanceof RightChatCell) {
                    RightChatCell rightChatCell = (RightChatCell) holder.itemView;
                    rightChatCell.setViewData(getItem(position));
                }

                if (holder.itemView instanceof LeftChatCell) {
                    LeftChatCell leftChatCell = (LeftChatCell) holder.itemView;
                    leftChatCell.setViewData(getItem(position));
                }
            }

            @Override
            public int getItemViewType(int position) {
                RMessage message = getItem(position);
                if (message != null && UserData.getInstance().isUser(message.getUserId())) {
                    return 2;
                } else {
                    return 1;
                }
            }
        };
    }

//    private void logMessages() {
//        Log.d(TAG, " ");
//        for (RMessage message : messages) {
//            message.log();
//        }
//        Log.d(TAG, " ");
//    }
}

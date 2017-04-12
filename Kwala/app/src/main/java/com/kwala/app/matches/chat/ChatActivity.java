package com.kwala.app.matches.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kwala.app.R;
import com.kwala.app.helpers.SimpleTextWatcher;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KRealmRecyclerViewAdapter;
import com.kwala.app.helpers.views.KwalaEditText;
import com.kwala.app.models.RMessage;
import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.firebase.ChatObserver;
import com.kwala.app.service.realm.RealmQueries;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.chat.SendMessageTask;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */
public class ChatActivity extends BaseActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

    /*
        References
     */
    private RecyclerView recyclerView;
    private KRealmRecyclerViewAdapter<RMessage> adapter;
    private ImageView sendButton;

    private KwalaEditText messageEditText;

    /*
        Data
     */
    private String matchId;
    private RealmResults<RMessage> messages;

    /*
        Constructors
     */
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
        messageEditText = (KwalaEditText) findViewById(R.id.chat_activity_text_field);
        sendButton = (ImageView) findViewById(R.id.chat_activity_send_button);

        /*
         * Set view data
         */
        matchId = getIntent().getStringExtra(MATCH_ID_KEY);

        ChatObserver chatObserver = new ChatObserver(matchId);
        chatObserver.startListening();

        adapter = createAdapter();
        recyclerView.setAdapter(adapter);

        messageEditText.addTextChangedListener(messageTextWatcher);
        sendButton.setOnClickListener(sendClickListener);
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

                Log.d(TAG, position + ": " + getItem(position).getMessageId());

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

    /**
     * Listeners
     */
    private final SimpleTextWatcher messageTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            boolean enabled = s.toString().trim().length() > 0;
            sendButton.setEnabled(enabled);
            sendButton.setAlpha(enabled ? 1.0f : 0.4f);
        }
    };

    private final View.OnClickListener sendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String messageText = messageEditText.getTextTrimmed();
            messageEditText.setText("");

            new SendMessageTask(matchId, messageText).start(new Task.Callback<Void, NetworkException>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "success!");
                }

                @Override
                public void onFailure(NetworkException e) {
                    Log.e(TAG, "Error sending message", e);
                }
            });
        }
    };
}

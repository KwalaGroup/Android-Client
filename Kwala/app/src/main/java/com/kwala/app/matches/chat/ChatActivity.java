package com.kwala.app.matches.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;

/**
 * @author jacobamuchow@gmail.com
 */
public class ChatActivity extends BaseActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

    public static Intent newIntent(Context context, String matchId) {
        Intent intent = new Intent(context, ChatActivity.class);

        intent.putExtra(MATCH_ID_KEY, matchId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
    }
}

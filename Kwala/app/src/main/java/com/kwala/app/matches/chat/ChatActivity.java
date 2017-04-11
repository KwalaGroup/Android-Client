package com.kwala.app.matches.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.models.FBMessage;
import com.kwala.app.service.UserData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */
public class ChatActivity extends BaseActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    private static final String MATCH_ID_KEY = "match_id";

    private DatabaseReference dbReference;

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

        dbReference = FirebaseDatabase.getInstance().getReference("match-chats").child(matchId);

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "data: " + dataSnapshot.toString());

                if (dataSnapshot.getValue() == null) {
                    Log.d(TAG, "null value");

                    final ArrayList<HashMap<String, Object>> messages = new ArrayList<>();

                    HashMap<String, Object> message = new HashMap<>();
                    message.put("text", "hey");
                    messages.add(message);

                    message = new HashMap<>();
                    message.put("text", "hi!");
                    messages.add(message);

                    dataSnapshot.getRef().setValue(messages);
                }

                DatabaseReference ref = dbReference.push();

                UserData userData = UserData.getInstance();
                FBMessage message = new FBMessage(userData.getUserId(), userData.getFirstName(), userData.getLastName(), "I'm at Harpos! you?");

                ref.setValue(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Firebase database error", databaseError.toException());
            }
        });
    }
}

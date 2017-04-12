package com.kwala.app.service.tasks.chat;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kwala.app.models.FBMessage;
import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;

/**
 * @author jacobamuchow@gmail.com
 */

public class SendMessageTask extends Task<Void, NetworkException> {
    private static final String TAG = SendMessageTask.class.getSimpleName();

    private String matchId;
    private String messageText;

    public SendMessageTask(String matchId, String message) {
        this.matchId = matchId;
        this.messageText = message.trim();
    }

    @Override
    protected void run() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("match-chats/" + matchId).push();
        UserData userData = UserData.getInstance();

        FBMessage message = new FBMessage(userData.getUserId(), userData.getFirstName(), userData.getLastName(), messageText);

        databaseReference.setValue(message, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    rejectOnMain(new NetworkException(databaseError.toException()));
                    return;
                }

                //Observer should get update with new message to be stored in Realm
                resolveOnMain(null);
            }
        });
    }
}

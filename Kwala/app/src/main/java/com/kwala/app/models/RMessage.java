package com.kwala.app.models;

import android.util.Log;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RMessage extends RealmObject {
    private static final String TAG = RMessage.class.getSimpleName();

    @PrimaryKey private String messageId;
    private Date createdDate;

    private String matchId;
    private String userId;
    private String firstName;
    private String lastName;
    private String text;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void log() {
        Log.d(TAG, messageId + " " + userId + " " + firstName + " " + lastName + " " + text);
    }
}

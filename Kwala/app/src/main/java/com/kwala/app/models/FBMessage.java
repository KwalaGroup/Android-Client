package com.kwala.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.UUID;

/**
 * @author jacobamuchow@gmail.com
 */
@IgnoreExtraProperties
public class FBMessage {
    private static final String TAG = FBMessage.class.getSimpleName();

    private String messageId;
    private long createdDate;

    private String userId;
    private String firstName;
    private String lastName;
    private String text;

    public FBMessage() {
        //Required for Firebase
    }

    public FBMessage(String userId, String firstName, String lastName, String text) {
        this.messageId = UUID.randomUUID().toString();
        this.createdDate = new Date().getTime();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.text = text;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
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
}

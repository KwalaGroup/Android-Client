package com.kwala.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * @author jacobamuchow@gmail.com
 */
@IgnoreExtraProperties
public class FBMessage {
    private static final String TAG = FBMessage.class.getSimpleName();

    private String userId;
    private String firstName;
    private String lastName;
    private String text;

    public FBMessage() {
        //Required for Firebase
    }

    public FBMessage(String userId, String firstName, String lastName, String text) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.text = text;
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

package com.kwala.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.kwala.app.KwalaApplication;

/**
 * @author jacobamuchow@gmail.com
 */
public class UserData {
    private static final String TAG = UserData.class.getSimpleName();

    private static final String SHARED_PREFS_KEY = "user_data_shared_prefs";

    private static final class Keys {
        private static final String USER_ID = "user_id";
        //TODO: createdAt?
        private static final String EMAIL = "email";
        private static final String ACCOUNT_STATE = "account_state";
    }

    private static UserData userData;

    private SharedPreferences sharedPreferences;

    /*
        Constructors
     */
    private UserData(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    }

    public static UserData getInstance() {
        if (userData == null) {
            userData = new UserData(KwalaApplication.getInstance());
        }
        return userData;
    }

    @Nullable
    public String getUserId() {
        return sharedPreferences.getString(Keys.USER_ID, null);
    }

    public UserData setUserId(@Nullable String userId) {
        sharedPreferences.edit().putString(Keys.USER_ID, userId).apply();
        return this;
    }

    @Nullable
    public String getEmail() {
        return sharedPreferences.getString(Keys.EMAIL, null);
    }

    public UserData setEmail(@Nullable String email) {
        sharedPreferences.edit().putString(Keys.EMAIL, email).apply();
        return this;
    }

    @Nullable
    public String getAccountStateValue() {
        return sharedPreferences.getString(Keys.ACCOUNT_STATE, null);
    }

    public UserData setAccountStateValue(String accountStateValue) {
        sharedPreferences.edit().putString(Keys.ACCOUNT_STATE, accountStateValue).apply();
        return this;
    }
}

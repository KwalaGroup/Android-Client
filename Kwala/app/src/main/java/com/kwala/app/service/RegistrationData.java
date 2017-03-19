package com.kwala.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.kwala.app.KwalaApplication;

/**
 * @author jacobamuchow@gmail.com
 */

public class RegistrationData {
    private static final String TAG = RegistrationData.class.getSimpleName();

    private static final String SHARED_PREFS_KEY = "registrationData";

    private static final class Keys {
        private static final String EMAIL = "email";
    }

    private static RegistrationData registrationData;

    private SharedPreferences sharedPreferences;

    private RegistrationData(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    }

    public static RegistrationData getInstance() {
        if (registrationData == null) {
            registrationData = new RegistrationData(KwalaApplication.getInstance());
        }
        return registrationData;
    }

    @Nullable
    public String getEmail() {
        return sharedPreferences.getString("email", null);
    }

    public RegistrationData setEmail(@Nullable String email) {
        sharedPreferences.edit().putString(Keys.EMAIL, email).apply();
        return this;
    }
}

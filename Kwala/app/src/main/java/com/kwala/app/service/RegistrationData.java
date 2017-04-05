package com.kwala.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.kwala.app.enums.Gender;
import com.kwala.app.main.KwalaApplication;

/**
 * @author jacobamuchow@gmail.com
 */

public class RegistrationData {
    private static final String TAG = RegistrationData.class.getSimpleName();

    private static final String SHARED_PREFS_KEY = "registrationData";

    private static final class Keys {
        private static final String EMAIL = "email";
        private static final String HASHED_PASSWORD = "hashed_password";
        private static final String FIRST_NAME = "first_name";
        private static final String LAST_NAME = "last_name";
        private static final String AGE = "age";
        private static final String PROFILE_IMAGE_ID = "profile_image_id";
        private static final String GENDER = "gender";
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

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    @Nullable
    public String getEmail() {
        return sharedPreferences.getString(Keys.EMAIL, null);
    }

    public RegistrationData setEmail(@Nullable String email) {
        sharedPreferences.edit().putString(Keys.EMAIL, email).apply();
        return this;
    }

    /**
     * Password
     */
    @Nullable
    public String getHashedPassword() {
        return sharedPreferences.getString(Keys.HASHED_PASSWORD, null);
    }

    public RegistrationData setHashedPassword(@Nullable String hashedPassword) {
        sharedPreferences.edit().putString(Keys.HASHED_PASSWORD, hashedPassword).apply();
        return this;
    }

    /**
     * First Name
     */
    @Nullable
    public String getFirstName() {
        return sharedPreferences.getString(Keys.FIRST_NAME, null);
    }

    public RegistrationData setFirstName(@Nullable String firstName) {
        sharedPreferences.edit().putString(Keys.FIRST_NAME, firstName).apply();
        return this;
    }

    /**
     * Last Name
     */
    @Nullable
    public String getLastName() {
        return sharedPreferences.getString(Keys.LAST_NAME, null);
    }

    public RegistrationData setLastName(@Nullable String lastName) {
        sharedPreferences.edit().putString(Keys.LAST_NAME, lastName).apply();
        return this;
    }

    /**
     * Age
     */
    @Nullable
    public Integer getAge() {
        int age = sharedPreferences.getInt(Keys.AGE, 0);
        return age == 0 ? null : age;
    }

    public RegistrationData setAge(@Nullable Integer age) {
        if (age == null) {
            sharedPreferences.edit().remove(Keys.AGE).apply();
        } else {
            sharedPreferences.edit().putInt(Keys.AGE, age).apply();
        }
        return this;
    }

    /**
     * Profile image ID
     */
    @Nullable
    public String getProfileImageId() {
        return sharedPreferences.getString(Keys.PROFILE_IMAGE_ID, null);
    }

    public RegistrationData setProfileImageId(@Nullable String imageId) {
        sharedPreferences.edit().putString(Keys.PROFILE_IMAGE_ID, imageId).apply();
        return this;
    }

    /**
     * Gender
     */
    public Gender getGender() {
        String genderValue = sharedPreferences.getString(Keys.GENDER, Gender.UNKNOWN.getNetworkValue());
        return Gender.fromNetworkValue(genderValue);
    }

    public RegistrationData setGender(Gender gender) {
        sharedPreferences.edit().putString(Keys.GENDER, gender.getNetworkValue()).apply();
        return this;
    }
}

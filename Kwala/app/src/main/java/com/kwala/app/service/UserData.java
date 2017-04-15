package com.kwala.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.kwala.app.enums.Gender;
import com.kwala.app.helpers.Tools;
import com.kwala.app.main.KwalaApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */
public class UserData {
    private static final String TAG = UserData.class.getSimpleName();

    private static final String SHARED_PREFS_KEY = "user_data_shared_prefs";

    private static final class Keys {
        private static final String ENTERED_EMAIL = "entered_email";
        private static final String HASHED_PASSWORD = "hashed_password";
        private static final String USER_ID = "user_id";
        private static final String EMAIL = "email";
        private static final String FIRST_NAME = "first_name";
        private static final String LAST_NAME = "last_name";
        private static final String PROFILE_IMAGE_ID = "profile_image_id";
        private static final String GENDER = "gender";
        private static final String AGE = "age";
        private static final String BIO = "bio";
        private static final String COLOR = "color";
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

    public synchronized void clearData() {
        sharedPreferences.edit().clear().apply();
    }

    @Nullable
    public synchronized String getEnteredEmail() {
        return sharedPreferences.getString(Keys.ENTERED_EMAIL, null);
    }

    public synchronized UserData setEnteredEmail(@Nullable String enteredEmail) {
        sharedPreferences.edit().putString(Keys.ENTERED_EMAIL, enteredEmail).apply();
        return this;
    }

    @Nullable
    public synchronized String getHashedPassword() {
        return sharedPreferences.getString(Keys.HASHED_PASSWORD, null);
    }

    public synchronized UserData setHashedPassowrd(@Nullable String hashedPassword) {
        sharedPreferences.edit().putString(Keys.HASHED_PASSWORD, hashedPassword).apply();
        return this;
    }

    @Nullable
    public synchronized String getUserId() {
        return sharedPreferences.getString(Keys.USER_ID, null);
    }

    public synchronized UserData setUserId(@Nullable String userId) {
        sharedPreferences.edit().putString(Keys.USER_ID, userId).apply();
        return this;
    }

    public synchronized boolean isLoggedIn() {
        return getUserId() != null;
    }

    public synchronized boolean isUser(@Nullable String userId) {
        return TextUtils.equals(getUserId(), userId);
    }

    @Nullable
    public synchronized String getEmail() {
        return sharedPreferences.getString(Keys.EMAIL, null);
    }

    public synchronized UserData setEmail(@Nullable String email) {
        sharedPreferences.edit().putString(Keys.EMAIL, email).apply();
        return this;
    }

    @Nullable
    public synchronized String getFirstName() {
        return sharedPreferences.getString(Keys.FIRST_NAME, null);
    }

    public synchronized UserData setFirstName(@Nullable String firstName) {
        sharedPreferences.edit().putString(Keys.FIRST_NAME, firstName).apply();
        return this;
    }

    @Nullable
    public synchronized String getLastName() {
        return sharedPreferences.getString(Keys.LAST_NAME, null);
    }

    public synchronized UserData setLastName(@Nullable String lastName) {
        sharedPreferences.edit().putString(Keys.LAST_NAME, lastName).apply();
        return this;
    }

    @Nullable
    public synchronized String getProfileImageId() {
        return sharedPreferences.getString(Keys.PROFILE_IMAGE_ID, null);
    }

    public synchronized UserData setProfileImageId(@Nullable String profileImageId) {
        sharedPreferences.edit().putString(Keys.PROFILE_IMAGE_ID, profileImageId).apply();
        return this;
    }

    public synchronized String getGenderValue() {
        return sharedPreferences.getString(Keys.GENDER, Gender.UNKNOWN.getNetworkValue());
    }

    public synchronized Gender getGender() {
        return Gender.fromNetworkValue(getGenderValue());
    }

    public synchronized UserData setGenderValue(String networkValue) {
        sharedPreferences.edit().putString(Keys.GENDER, networkValue).apply();
        return this;
    }

    public synchronized UserData setGender(Gender gender) {
        return setGenderValue(gender.getNetworkValue());
    }

    @Nullable
    public synchronized Integer getAge() {
        int value = sharedPreferences.getInt(Keys.AGE, 0);
        return value == 0 ? null : value;
    }

    public synchronized UserData setAge(@Nullable Integer age) {
        sharedPreferences.edit().putInt(Keys.AGE, age == null ? 0 : age).apply();
        return this;
    }

    @Nullable
    public synchronized String getBio() {
        return sharedPreferences.getString(Keys.BIO, null);
    }

    public synchronized UserData setBio(@Nullable String bio) {
        sharedPreferences.edit().putString(Keys.BIO, bio).apply();
        return this;
    }

    @Nullable
    public synchronized String getColor() {
        return sharedPreferences.getString(Keys.COLOR, null);
    }

    @ColorInt
    public synchronized int getColorAsInt() {
        String colorHex = getColor();
        return Tools.hexToColorInt(colorHex == null ? "ffa9deef" : colorHex);
    }

    public synchronized UserData setColor(@Nullable String color) {
        sharedPreferences.edit().putString(Keys.COLOR, color).apply();
        return this;
    }

    public void updateFromJson(JSONObject jsonObject) throws JSONException {

        setUserId(jsonObject.getString("id"));

        setEmail(jsonObject.getString("email"));

        setFirstName(jsonObject.getString("first_name"));

        setLastName(jsonObject.getString("last_name"));

        setProfileImageId(jsonObject.getString("image_id"));

        setGenderValue(jsonObject.getString("gender"));

        setAge(jsonObject.getInt("age"));

        setBio(jsonObject.optString("bio", null));

        setColor(jsonObject.getString("color"));
    }
}

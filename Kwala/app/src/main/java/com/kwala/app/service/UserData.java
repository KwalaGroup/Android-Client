package com.kwala.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.kwala.app.enums.Gender;
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
        private static final String USER_ID = "user_id";
        private static final String EMAIL = "email";
        private static final String FIRST_NAME = "first_name";
        private static final String LAST_NAME = "last_name";
        private static final String GENDER = "gender";
        private static final String AGE = "age";
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

    public void updateFromJson(JSONObject jsonObject) throws JSONException {

        setUserId(jsonObject.getString("id"));

        setEmail(jsonObject.getString("email"));

        setFirstName(jsonObject.getString("first_name"));

        setLastName(jsonObject.getString("last_name"));

        setGenderValue(jsonObject.getString("gender"));

        setAge(jsonObject.getInt("age"));
    }
}

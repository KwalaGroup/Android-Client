package com.kwala.app.service.tasks.auth;

import android.util.Log;

import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.auth.LoginEndpoint;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class LoginTask extends NetworkTask<Void> {
    private static final String TAG = LoginTask.class.getSimpleName();

    private String email;
    private String hashedPassword;

    public LoginTask(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new LoginEndpoint(email, hashedPassword);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {

        String userId = jsonObject.getString("user_id");
        Log.e(TAG, "userId: " + userId);

        UserData.getInstance().setUserId(userId);

        return null;
    }
}

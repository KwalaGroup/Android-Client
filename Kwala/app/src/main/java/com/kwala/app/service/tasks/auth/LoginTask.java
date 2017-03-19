package com.kwala.app.service.tasks.auth;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.auth.LoginEndpoint;
import com.kwala.app.service.tasks.Task;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class LoginTask extends Task<Void> {
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
    protected Void parse(JSONObject jsonObject) {
        Log.d(TAG, jsonObject.toString());
        return null;
    }
}

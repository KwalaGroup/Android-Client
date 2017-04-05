package com.kwala.app.service.tasks.auth;

import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
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
        return new APIEndpoint(APIPaths.Auth.LOGIN, Endpoint.Method.POST)
                .addParam("email", email)
                .addParam("password", hashedPassword);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {

        UserData.getInstance().updateFromJson(jsonObject);

        return null;
    }
}

package com.kwala.app.service.tasks.auth;

import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class EmailAvailabilityTask extends NetworkTask<Boolean> {
    private static final String TAG = EmailAvailabilityTask.class.getSimpleName();

    private String email;

    public EmailAvailabilityTask(String email) {
        this.email = email;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.Auth.EMAIL_AVAILABILITY, Endpoint.Method.GET)
                .addParam("email", email);
    }

    @Override
    protected Boolean parse(JSONObject jsonObject) throws JSONException {
        return jsonObject.getBoolean("is_available");
    }
}

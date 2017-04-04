package com.kwala.app.service.tasks.auth;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.auth.EmailAvailabilityEndpoint;
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
        return new EmailAvailabilityEndpoint(email);
    }

    @Override
    protected Boolean parse(JSONObject jsonObject) throws JSONException {
        return jsonObject.getBoolean("is_available");
    }
}

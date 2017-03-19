package com.kwala.app.service.tasks.auth;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.auth.EmailAvailabilityEndpoint;
import com.kwala.app.service.tasks.Task;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class EmailAvailabilityTask extends Task<Void> {
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
    protected Void parse(JSONObject jsonObject) {
        Log.d(TAG, jsonObject.toString());
        return null;
    }
}

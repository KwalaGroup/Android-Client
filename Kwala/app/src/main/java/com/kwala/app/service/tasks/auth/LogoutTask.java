package com.kwala.app.service.tasks.auth;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.auth.LogoutEndpoint;
import com.kwala.app.service.tasks.Task;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class LogoutTask extends Task<Void> {
    private static final String TAG = LogoutTask.class.getSimpleName();

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new LogoutEndpoint();
    }

    @Override
    protected Void parse(JSONObject jsonObject) {
        Log.d(TAG, jsonObject.toString());
        return null;
    }
}

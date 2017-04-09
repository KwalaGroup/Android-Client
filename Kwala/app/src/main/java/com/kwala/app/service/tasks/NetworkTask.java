package com.kwala.app.service.tasks;

import android.util.Log;

import com.kwala.app.service.DataStore;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;
import com.kwala.app.service.endpoints.NetworkException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public abstract class NetworkTask<Result> extends Task<Result, NetworkException> {
    private static final String TAG = NetworkTask.class.getSimpleName();

    protected abstract Endpoint<JSONObject> buildEndpoint();

    protected abstract Result parse(JSONObject jsonObject) throws JSONException;

    @Override
    protected void run() {
        final Endpoint<JSONObject> endpoint = buildEndpoint();

        DataStore.getInstance().getNetworkStore().performRequest(endpoint, new EndpointRequest.Callback<JSONObject>() {
            @Override
            public void success(JSONObject response) {
                if (endpoint.shouldLog()) {
                    Log.d(TAG, "response: " + response);
                }

                try {
                    Result result = parse(response);
                    resolveOnMain(result);

                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing result", e);
                    rejectOnMain(new NetworkException("Error parsing result", e));
                    return;
                }
            }

            @Override
            public void failure(final NetworkException e) {
                rejectOnMain(e);
            }
        });
    }
}

package com.kwala.app.service;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jacobamuchow@gmail.com
 */
public class NetworkStore {
    private static final String TAG = NetworkStore.class.getSimpleName();

    private OkHttpClient okHttpClient;

    public NetworkStore() {
        okHttpClient = new OkHttpClient.Builder()
                .build();
    }

    public <T> EndpointRequest<T> performRequest(final Endpoint<T> endpoint, final EndpointRequest.Callback<T> callback) {

        Request request = OkRequestFactory.createRequest(endpoint);

        okhttp3.Callback responseCallback = new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response);
                try {
                    T result = endpoint.parse(response.code(), response.body().string());
                    callback.success(result);
                } catch (Exception e) {
                    callback.failure(e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(e);
            }
        };

        okHttpClient.newCall(request).enqueue(responseCallback);

        return new EndpointRequest<>(endpoint, callback);
    }
}

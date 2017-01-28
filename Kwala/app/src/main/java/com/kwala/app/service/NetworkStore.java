package com.kwala.app.service;

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

    public <T> EndpointRequest<T> performRequest(Endpoint<T> endpoint, final EndpointRequest.Callback callback) {

        Request request = OkRequestFactory.createRequest(endpoint);

        okhttp3.Callback responseCallback = new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.success(response.code(), response.body().toString());
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new Error(e));
            }
        };

        okHttpClient.newCall(request).enqueue(responseCallback);

        return new EndpointRequest<>(endpoint, callback);
    }
}

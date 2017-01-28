package com.kwala.app.service;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author jacobamuchow@gmail.com
 */
class OkRequestFactory {
    private static final String TAG = OkRequestFactory.class.getSimpleName();

    public static <T> Request createRequest(Endpoint<T> endpoint) {
        return new Request.Builder()
                .url(endpoint.getUrl())
                .method(getOkMethod(endpoint), getOkBody(endpoint))
                .build();
    }

    private static <T> String getOkMethod(Endpoint<T> endpoint) {
        switch (endpoint.getMethod()) {
            case GET: return "GET";
            case POST: return "POST";
            case PUT: return "PUT";
            case DELETE: return "DELETE";
            default: throw new IllegalStateException("Method not supported: " + endpoint.getMethod());
        }
    }

    private static <T> RequestBody getOkBody(Endpoint<T> endpoint) {
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> param : endpoint.getParams().entrySet()) {
            builder.add(param.getKey(), param.getValue());
        }

        return builder.build();
    }
}
